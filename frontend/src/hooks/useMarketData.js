// src/hooks/useMarketData.js
import { useEffect } from 'react';

export default function useMarketData(setData, symbols) {
  useEffect(() => {
    const ws = new WebSocket("ws://localhost:8002/ws/marketdata");

    ws.onopen = () => {
      console.log("Market Data WebSocket connected");
      if (symbols && symbols.length > 0) {
        ws.send(JSON.stringify({ symbols })); // optional
      }
    };

    ws.onmessage = (event) => {
      const data = JSON.parse(event.data);
      console.log("Market Data:", data);
      setData(data);
    };

    return () => ws.close();
  }, [setData, symbols]);
}
