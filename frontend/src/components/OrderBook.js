import React from 'react';
import { useOrderStatus } from '../hooks/useOrderStatus';

export default function OrderBook() {
  const orderStatus = useOrderStatus();

  return (
    <div>
      <h2>Order Book</h2>
      <table>
        <thead>
          <tr><th>Order ID</th><th>Status</th><th>Filled Qty</th></tr>
        </thead>
        <tbody>
          {orderStatus.map((status) => (
            <tr key={status.orderId}>
              <td>{status.orderId}</td>
              <td>{status.status}</td>
              <td>{status.filledQty}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
