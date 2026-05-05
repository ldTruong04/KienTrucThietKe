const redis = require('redis');

// Khởi tạo redis client với REDIS_URL từ biến môi trường, mặc định là localhost
const redisClient = redis.createClient({
  url: process.env.REDIS_URL || 'redis://localhost:6379'
});

redisClient.on('error', (err) => console.error('Redis Client Error', err));
redisClient.on('connect', () => console.log('Redis connected successfully'));

// Tự động kết nối
(async () => {
  await redisClient.connect();
})();

module.exports = redisClient;
