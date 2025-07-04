-- Drop for dev resets
DROP TABLE IF EXISTS market_data CASCADE;
DROP TABLE IF EXISTS orders CASCADE;

-- ✅ Market Data Table
CREATE TABLE IF NOT EXISTS market_data (
  symbol VARCHAR(20) PRIMARY KEY,
  name VARCHAR(100),
  last_price NUMERIC(12,4),
  bid NUMERIC(12,4),
  ask NUMERIC(12,4),
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ✅ Orders Table
CREATE TABLE IF NOT EXISTS orders (
  id SERIAL PRIMARY KEY,
  cl_ord_id VARCHAR(50) UNIQUE NOT NULL,
  orig_cl_ord_id VARCHAR(50),   -- For amendments/cancels
  symbol VARCHAR(20) NOT NULL,
  side VARCHAR(4) NOT NULL,
  qty NUMERIC(12,4) NOT NULL,
  price NUMERIC(12,4) NOT NULL,
  order_type VARCHAR(20) NOT NULL,
  client_id VARCHAR(50),
  time_in_force VARCHAR(20),
  venue VARCHAR(50),
  custom_tags JSONB,
  status VARCHAR(20) DEFAULT 'NEW',
  filled_qty NUMERIC(12,4) DEFAULT 0,
  exec_id VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
