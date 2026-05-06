require('dotenv').config();
const express = require("express");
const app = express();
const cors = require("cors");

app.use(cors());
app.use(express.json());

const users = [
  { id: 1, username: "khang", password: "123" }
];

app.post("/login", (req, res) => {
  const { username, password } = req.body;
  const user = users.find(u => u.username === username && u.password === password);

  if (user) res.json(user);
  else res.status(401).json({ error: "Invalid" });
});

app.post("/register", (req, res) => {
  const { username, password } = req.body;
  
  if (!username || !password) {
    return res.status(400).json({ error: "Username and password are required" });
  }

  const existingUser = users.find(u => u.username === username);
  if (existingUser) {
    return res.status(409).json({ error: "Username already exists" });
  }

  const newUser = {
    id: users.length > 0 ? Math.max(...users.map(u => u.id)) + 1 : 1,
    username,
    password
  };
  users.push(newUser);
  
  res.status(201).json(newUser);
});

app.get("/users/:id", (req, res) => {
  const user = users.find(u => u.id == req.params.id);
  res.json(user);
});

const PORT = process.env.PORT || 8081;
app.listen(PORT, () => console.log(`User Service running on port ${PORT}`));