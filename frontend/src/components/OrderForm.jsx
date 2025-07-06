import { useState } from 'react';
import axios from 'axios';

export default function OrderForm({ token }) {
  const [form, setForm] = useState({
    symbol: '',
    side: '',
    quantity: 0,
    price: 0.0,
    orderType: '',
    clientId: '',
    timeInForce: '',
    venue: '',
    customTags: '',
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const submitOrder = async (type) => {
    const url = `/api/order/${type}`;
    await axios.post(url, form, {
      headers: { Authorization: `Bearer ${token}` }
    });
    alert(`${type} order submitted`);
  };

  return (
    <div>
      {/* Inputs for form fields */}
      <input name="symbol" onChange={handleChange} placeholder="Symbol" />
      <input name="side" onChange={handleChange} placeholder="Side" />
      <input name="quantity" type="number" onChange={handleChange} placeholder="Qty" />
      <input name="price" type="number" onChange={handleChange} placeholder="Price" />
      <input name="orderType" onChange={handleChange} placeholder="Order Type" />
      <input name="clientId" onChange={handleChange} placeholder="Client ID" />
      <input name="timeInForce" onChange={handleChange} placeholder="Time In Force" />
      <input name="venue" onChange={handleChange} placeholder="Venue" />
      <input name="customTags" onChange={handleChange} placeholder="Custom Tags" />
      <button onClick={() => submitOrder('new')}>New Order</button>
      <button onClick={() => submitOrder('amend')}>Amend Order</button>
      <button onClick={() => submitOrder('cancel')}>Cancel Order</button>
    </div>
  );
}
