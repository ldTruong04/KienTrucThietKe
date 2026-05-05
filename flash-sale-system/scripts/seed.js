const path = require('path');
require('dotenv').config({ path: path.join(__dirname, '../.env') });
const redisClient = require('../shared/redisClient');

const products = [
  { id: 'p1', name: 'iPhone 15 Pro Max', price: 999, initialStock: 50, image: 'https://images.unsplash.com/photo-1695048133142-1a20484d2569?auto=format&fit=crop&q=80&w=400' },
  { id: 'p2', name: 'MacBook Pro M3', price: 1999, initialStock: 10, image: 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&q=80&w=400' },
  { id: 'p3', name: 'AirPods Pro 2', price: 249, initialStock: 100, image: 'https://images.unsplash.com/photo-1600294037681-c80b4cb5b434?auto=format&fit=crop&q=80&w=400' },
  { id: 'p4', name: 'Apple Watch Ultra', price: 799, initialStock: 20, image: 'https://tse1.mm.bing.net/th/id/OIP.LU72Ez-p8lx5k2joLmgzpgEsCo?rs=1&pid=ImgDetMain&o=7&rm=3?auto=format&fit=crop&q=80&w=400' },
];

async function seedData() {
  console.log('Đang dọn dẹp dữ liệu cũ (FLUSHDB)...');
  await redisClient.flushDb();

  console.log('Bắt đầu seed dữ liệu sản phẩm...');

  for (const product of products) {
    // Lưu danh sách id
    await redisClient.sAdd('products', product.id);

    // Lưu thông tin chi tiết
    await redisClient.hSet(`product:${product.id}`, {
      name: product.name,
      price: product.price,
      image: product.image
    });

    // Lưu stock riêng biệt để dễ dàng dùng incr/decr
    await redisClient.set(`stock:${product.id}`, product.initialStock);

    console.log(`Đã seed: ${product.name} (Stock: ${product.initialStock})`);
  }

  console.log('Hoàn thành seed dữ liệu!');
  process.exit(0);
}

// Chạy hàm seed
setTimeout(seedData, 1000);
