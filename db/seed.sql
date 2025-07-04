INSERT INTO market_data (symbol, name, last_price, bid, ask)
VALUES 
  ('AAPL', 'Apple Inc', 185.30, 185.25, 185.35),
  ('GOOGL', 'Alphabet Inc', 2800.50, 2800.00, 2801.00),
  ('TSLA', 'Tesla Inc', 750.00, 749.50, 750.50)
ON CONFLICT (symbol) DO UPDATE
SET last_price = EXCLUDED.last_price,
    bid = EXCLUDED.bid,
    ask = EXCLUDED.ask,
    updated_at = CURRENT_TIMESTAMP;
