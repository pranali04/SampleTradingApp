// src/components/OrderForm.js
import React, { useState } from 'react';
const token = localStorage.getItem('access_token');

export default function OrderForm({ token }) {
  const [symbol, setSymbol] = useState("AAPL");
  const [quantity, setQuantity] = useState(100);
  const [side, setSide] = useState("BUY");
  const [price, setPrice] = useState(185.75);
  const [orderType, setOrderType] = useState("LIMIT");
  const [clientID, setClientID] = useState("CLIENT123");
  const [timeInForce, setTimeInForce] = useState("GTC");
  const [venue, setVenue] = useState("NASDAQ");
  const [customTags, setCustomTags] = useState("");

  async function placeOrder() {
    const payload = {
      symbol, side, quantity, price, orderType,
      clientID, timeInForce, venue,
      customTags: JSON.parse(customTags || "{}")
    };
    
    const res = await fetch('/orders', {
      method: "POST",
      headers: { 
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify(payload)
    });

    const data = await res.json();
    console.log("Order placed:", data);
  }

  return (
    <div>
      <h2>New Order</h2>
      <input value={symbol} onChange={e => setSymbol(e.target.value)} placeholder="Symbol" /><br/>
      <input type="number" value={quantity} onChange={e => setQuantity(e.target.value)} placeholder="Quantity" /><br/>
      <select value={side} onChange={e => setSide(e.target.value)}>
        <option>BUY</option><option>SELL</option>
      </select><br/>
      <input type="number" value={price} onChange={e => setPrice(e.target.value)} placeholder="Price" /><br/>
      <select value={orderType} onChange={e => setOrderType(e.target.value)}>
        <option>LIMIT</option><option>MARKET</option>
      </select><br/>
      <input value={clientID} onChange={e => setClientID(e.target.value)} placeholder="ClientID" /><br/>
      <input value={timeInForce} onChange={e => setTimeInForce(e.target.value)} placeholder="TimeInForce" /><br/>
      <input value={venue} onChange={e => setVenue(e.target.value)} placeholder="Venue" /><br/>
      <textarea value={customTags} onChange={e => setCustomTags(e.target.value)} placeholder='{"strategy":"TWAP"}'></textarea><br/>
      <button onClick={placeOrder}>Place Order</button>
    </div>
  );
}
