import React from 'react';
import { useMarketData } from '../hooks/useMarketData';

export default function MarketDataDashboard({ token }) {
  const marketData = useMarketData(token);

  return (
    <div>
      <h2>Market Data</h2>
      <table>
        <thead>
          <tr><th>Symbol</th><th>Price</th><th>Bid</th><th>Ask</th></tr>
        </thead>
        <tbody>
          {marketData.map((item) => (
            <tr key={item.symbol}>
              <td>{item.symbol}</td>
              <td>{item.lastPrice}</td>
              <td>{item.bid}</td>
              <td>{item.ask}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
