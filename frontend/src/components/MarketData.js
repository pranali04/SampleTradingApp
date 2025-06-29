import React, { useState } from 'react';
import useMarketData from '../hooks/useMarketData';

export default function MarketData() {
  const [data, setData] = useState({});
  useMarketData(setData);

  return (
    <div>
      <h2>Market Data</h2>
      <pre>{JSON.stringify(data, null, 2)}</pre>
    </div>
  );
}
