const express = require('express');
const cors = require('cors');
const path = require('path');
const axios = require('axios');
const { v4: uuidv4 } = require('uuid');

require('dotenv').config({ path: path.join(__dirname, '../../.env') });
const redisClient = require('../../shared/redisClient');

const app = express();
app.use(cors());
app.use(express.json());

const PORT = process.env.ORDER_SERVICE_PORT || 8083;
const CART_SERVICE_URL = process.env.CART_SERVICE_URL || 'http://localhost:8082';
const INVENTORY_SERVICE_URL = process.env.INVENTORY_SERVICE_URL || 'http://localhost:8084';

// API: Checkout tạo đơn hàng
app.post('/checkout', async (req, res) => {
  try {
    const { userId } = req.body;

    if (!userId) {
      return res.status(400).json({ message: 'Missing userId' });
    }

    // 1. Lấy thông tin giỏ hàng qua Cart Service
    const cartRes = await axios.get(`${CART_SERVICE_URL}/cart/${userId}`);
    const items = cartRes.data.items;

    if (!items || items.length === 0) {
      return res.status(400).json({ message: 'Cart is empty' });
    }

    // 2. Giảm số lượng kho (Reserve Inventory)
    // Lưu ý: Ở một hệ thống thực tế phức tạp, ta có thể dùng saga pattern để rollback
    // Ở đây ta gọi Inventory API trực tiếp (atomic decrease qua Lua Script)
    const successItems = [];
    const failedItems = [];

    for (const item of items) {
      try {
        await axios.post(`${INVENTORY_SERVICE_URL}/stock/decrease`, {
          productId: item.productId,
          quantity: item.quantity
        });
        successItems.push(item);
      } catch (error) {
        // Lỗi thường do hết hàng (400)
        failedItems.push(item.productId);
      }
    }

    // Nếu không có sản phẩm nào trừ kho thành công
    if (successItems.length === 0) {
      return res.status(400).json({ message: 'Checkout failed. Items out of stock.', failedItems });
    }

    // 3. Tạo order cho các sản phẩm mua thành công
    const orderId = `ord_${uuidv4()}`;
    const orderData = {
      orderId,
      userId,
      items: JSON.stringify(successItems), // Lưu mảng dưới dạng JSON string trong Redis Hash
      status: 'CONFIRMED',
      timestamp: Date.now()
    };

    // Lưu order vào Redis để Data Grid quản lý (có thể async flush xuống DB sau)
    await redisClient.hSet(`order:${orderId}`, orderData);
    
    // Lưu vào danh sách order của user
    await redisClient.sAdd(`user_orders:${userId}`, orderId);

    // 4. Xóa giỏ hàng
    await axios.post(`${CART_SERVICE_URL}/cart/clear`, { userId });

    res.json({
      message: 'Checkout completed successfully',
      orderId,
      successItems,
      failedItems: failedItems.length > 0 ? failedItems : undefined
    });

  } catch (error) {
    console.error('Lỗi khi checkout:', error.response?.data || error.message);
    res.status(500).json({ message: 'Internal Server Error' });
  }
});

app.listen(PORT, () => {
  console.log(`Order Service is running on port ${PORT}`);
});

// API: Lấy danh sách lịch sử đơn hàng của User
app.get('/orders/:userId', async (req, res) => {
  try {
    const { userId } = req.params;
    
    // Lấy toàn bộ orderId thuộc về user
    const orderIds = await redisClient.sMembers(`user_orders:${userId}`);
    
    if (!orderIds || orderIds.length === 0) {
      return res.json([]);
    }

    // Dùng Pipeline lấy chi tiết các order để tối ưu tốc độ O(1)
    const pipeline = redisClient.multi();
    orderIds.forEach(id => {
      pipeline.hGetAll(`order:${id}`);
    });

    const results = await pipeline.exec();
    
    const orders = results
      .filter(order => Object.keys(order).length > 0)
      .map(order => ({
        ...order,
        items: JSON.parse(order.items), // parse mảng items
        timestamp: parseInt(order.timestamp)
      }))
      .sort((a, b) => b.timestamp - a.timestamp); // Sắp xếp mới nhất lên đầu

    res.json(orders);
  } catch (error) {
    console.error('Lỗi khi lấy danh sách đơn hàng:', error);
    res.status(500).json({ message: 'Internal Server Error' });
  }
});
