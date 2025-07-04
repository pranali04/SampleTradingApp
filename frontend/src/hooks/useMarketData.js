import { useEffect, useState } from 'react';
import axios from 'axios';

export function useMarketData(token) {
  const [marketData, setMarketData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const res = await axios.get('/api/marketdata/all', {
        headers: { Authorization: `Bearer ${token}` }
      });
      setMarketData(res.data);
    };
    fetchData();
  }, [token]);

  return marketData;
}
