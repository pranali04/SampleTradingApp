// src/components/OrderStatus.js
import React, { useState } from 'react';
import useOrderStatus from '../hooks/useOrderStatus';

export default function OrderStatus({ token }) {
  const [orders, setOrders] = useState([]);
  useOrderStatus((data) => {
    console.log("Order Status WS:", data);
    setOrders(prev => {
      const idx = prev.findIndex(o => o.id === data.id);
      if (idx !== -1) {
        const updated = [...prev];
        updated[idx] = data;
        return updated;
      }
      return [...prev, data];
    });
  });

  async function amendOrder(orderId) {
    const newQty = prompt("New Quantity:");
    const newPrice = prompt("New Price:");

    const res = await fetch(`http://localhost:8000/order/${orderId}/amend`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify({ quantity: Number(newQty), price: Number(newPrice) })
    });

    const data = await res.json();
    console.log("Amend result:", data);
  }

  async function cancelOrder(orderId) {
    const res = await fetch(`http://localhost:8000/order/${orderId}/cancel`, {
      method: "POST",
      headers: { "Authorization": `Bearer ${token}` }
    });

    const data = await res.json();
    console.log("Cancel result:", data);
  }

  return (
    <div>
      <h2>Order Book</h2>
      {orders.map(order => (
        <div key={order.id} style={{ border: "1px solid gray", margin: "5px", padding: "5px" }}>
          <p>{order.symbol} {order.side} {order.quantity}@{order.price}</p>
          <p>Status: {order.status} | Filled: {order.filledQty || 0}</p>
          <button onClick={() => amendOrder(order.id)}>Amend</button>
          <button onClick={() => cancelOrder(order.id)}>Cancel</button>
        </div>
      ))}
    </div>
  );
}
