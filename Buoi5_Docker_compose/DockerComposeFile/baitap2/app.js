const express = require('express');
const mongoose = require('mongoose');

const app = express();
const PORT = 3000;

app.use(express.json());

// Kết nối MongoDB
const MONGO_URI = process.env.MONGO_URI || 'mongodb://mongo:27017/mydb';

mongoose.connect(MONGO_URI)
  .then(() => console.log('Connected to MongoDB'))
  .catch((err) => console.error('MongoDB connection error:', err));

// Định nghĩa Schema và Model
const itemSchema = new mongoose.Schema({
  name: { type: String, required: true },
  description: { type: String, default: '' },
  createdAt: { type: Date, default: Date.now },
});

const Item = mongoose.model('Item', itemSchema);

// API Routes

// GET - Lấy tất cả items
app.get('/api/items', async (req, res) => {
  try {
    const items = await Item.find();
    res.json(items);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// POST - Tạo item mới
app.post('/api/items', async (req, res) => {
  try {
    const item = new Item(req.body);
    const saved = await item.save();
    res.status(201).json(saved);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
});

// GET - Lấy item theo ID
app.get('/api/items/:id', async (req, res) => {
  try {
    const item = await Item.findById(req.params.id);
    if (!item) return res.status(404).json({ error: 'Item not found' });
    res.json(item);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// DELETE - Xóa item
app.delete('/api/items/:id', async (req, res) => {
  try {
    const item = await Item.findByIdAndDelete(req.params.id);
    if (!item) return res.status(404).json({ error: 'Item not found' });
    res.json({ message: 'Item deleted' });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Root
app.get('/', (req, res) => {
  res.json({ message: 'Node.js REST API with MongoDB' });
});

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
