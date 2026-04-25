const express = require('express');
const { Pool } = require('pg');

const app = express();
const PORT = 5001;

const pool = new Pool({
  host: process.env.POSTGRES_HOST || 'postgres',
  port: 5432,
  user: process.env.POSTGRES_USER || 'postgres',
  password: process.env.POSTGRES_PASSWORD || 'postgres',
  database: process.env.POSTGRES_DB || 'votes',
});

const TEMPLATE = `
<!DOCTYPE html>
<html>
<head>
    <title>Voting Results</title>
    <style>
        body { font-family: Arial, sans-serif; background: #1a1a2e; color: #fff; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }
        .container { text-align: center; width: 600px; }
        h1 { font-size: 2.5rem; margin-bottom: 30px; }
        .bar-container { background: #333; border-radius: 10px; overflow: hidden; height: 60px; display: flex; margin: 20px 0; }
        .bar-a { background: #0074D9; display: flex; align-items: center; justify-content: center; min-width: 40px; transition: width 0.5s; }
        .bar-b { background: #FF4136; display: flex; align-items: center; justify-content: center; min-width: 40px; transition: width 0.5s; }
        .label { font-size: 1.1rem; font-weight: bold; }
    </style>
    <meta http-equiv="refresh" content="3">
</head>
<body>
    <div class="container">
        <h1>📊 Results</h1>
        <div class="bar-container">
            <div class="bar-a" style="width: PERCENT_A%"><span class="label">Cats: COUNT_A</span></div>
            <div class="bar-b" style="width: PERCENT_B%"><span class="label">Dogs: COUNT_B</span></div>
        </div>
        <p>Total votes: TOTAL</p>
    </div>
</body>
</html>
`;

app.get('/', async (req, res) => {
  try {
    const result = await pool.query(
      'SELECT vote, COUNT(*) as count FROM votes GROUP BY vote'
    );
    let countA = 0, countB = 0;
    result.rows.forEach((row) => {
      if (row.vote === 'a') countA = parseInt(row.count);
      if (row.vote === 'b') countB = parseInt(row.count);
    });
    const total = countA + countB;
    const percentA = total > 0 ? ((countA / total) * 100).toFixed(1) : 50;
    const percentB = total > 0 ? ((countB / total) * 100).toFixed(1) : 50;

    let html = TEMPLATE
      .replace('PERCENT_A', percentA)
      .replace('PERCENT_B', percentB)
      .replace('COUNT_A', countA)
      .replace('COUNT_B', countB)
      .replace('TOTAL', total);
    res.send(html);
  } catch (err) {
    res.status(500).send('Waiting for database... ' + err.message);
  }
});

app.listen(PORT, () => {
  console.log('Result app listening on port ' + PORT);
});
