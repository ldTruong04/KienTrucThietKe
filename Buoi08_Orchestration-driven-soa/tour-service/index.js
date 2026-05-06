require('dotenv').config();
const express = require("express");
const app = express();

const tours = [
  { id: 1, name: "Đà Lạt", price: 100 },
  { id: 2, name: "Phú Quốc", price: 200 },
  { id: 3, name: "Sapa", price: 150 }
];

app.use(express.json());

app.get("/tours", (req, res) => res.json(tours));

app.get("/tours/:id", (req, res) => {
  const tour = tours.find(t => t.id == req.params.id);
  res.json(tour);
});

const PORT = process.env.PORT || 8082;
app.listen(PORT, () => console.log(`Tour Service running on port ${PORT}`));