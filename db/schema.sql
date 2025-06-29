CREATE TABLE market_data (
  symbol TEXT PRIMARY KEY,
  name TEXT,
  last_price FLOAT,
  bid FLOAT,
  ask FLOAT
);

CREATE TABLE IF NOT EXISTS orders (
  id SERIAL PRIMARY KEY,
  symbol TEXT,
  side TEXT,
  quantity INT,
  price FLOAT,
  order_type TEXT,
  client_id TEXT,
  time_in_force TEXT,
  venue TEXT,
  custom_tags JSONB,
  status TEXT,
  filled_qty INT DEFAULT 0
);

