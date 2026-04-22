require('dotenv').config();
const express = require('express');
const cors = require('cors');
const axios = require('axios');
const { Kafka } = require('kafkajs');

const app = express();
app.use(cors());
app.use(express.json());

const PORT = process.env.PORT || 8083;

// --- KAFKA SETUP ---
const kafka = new Kafka({
  clientId: 'booking-service',
  brokers: [process.env.KAFKA_BROKER]
});
const producer = kafka.producer();

const initKafka = async () => {
  try {
    await producer.connect();
    console.log('Connected to Kafka successfully');
  } catch (error) {
    console.error('Failed to connect to Kafka:', error);
  }
};
initKafka();

// --- GATEWAY ROUTES ---

// Auth
app.post('/login', async (req, res) => {
  try {
    const response = await axios.post(`${process.env.USER_SERVICE_URL}/login`, req.body);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { message: "User Service unavailable" });
  }
});

app.post('/register', async (req, res) => {
  try {
    const response = await axios.post(`${process.env.USER_SERVICE_URL}/register`, req.body);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { message: "User Service unavailable" });
  }
});

// Movies
app.get('/movies', async (req, res) => {
  try {
    const response = await axios.get(`${process.env.MOVIE_SERVICE_URL}/movies`);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { message: "Movie Service unavailable" });
  }
});

app.post('/movies', async (req, res) => {
  try {
    const response = await axios.post(`${process.env.MOVIE_SERVICE_URL}/movies`, req.body);
    res.status(201).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { message: "Failed to add movie via Movie Service" });
  }
});

// In-memory storage for demo (Should use a DB in production)
const bookings = [
  { id: 101, movieTitle: "Interstellar", seats: ["A1", "A2"], totalAmount: 200000, status: "COMPLETED", createdAt: new Date() }
];

// Bookings
app.post('/bookings', async (req, res) => {
  const { movieTitle, seats, totalAmount } = req.body;
  const bookingId = Math.floor(Math.random() * 1000000);
  
  const newBooking = {
    id: bookingId,
    movieTitle,
    seats,
    totalAmount,
    status: "PENDING",
    createdAt: new Date()
  };

  try {
    await producer.send({
      topic: 'BOOKING_CREATED',
      messages: [{ value: JSON.stringify({ bookingId: bookingId }) }],
    });

    bookings.push(newBooking); // Lưu vào danh sách tạm

    res.status(201).json({
      message: "Booking request received",
      bookingId: bookingId,
      status: "PENDING"
    });
  } catch (error) {
    res.status(500).json({ message: "Failed to process booking" });
  }
});

app.get('/bookings', (req, res) => {
  res.json(bookings);
});

app.listen(PORT, () => {
  console.log(`Booking Service (Gateway) running on port ${PORT}`);
});
