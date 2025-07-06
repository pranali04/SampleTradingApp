import React, { useState } from 'react';

export default function Login({ setToken }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  async function handleLogin() {
    const res = await fetch('/auth/login', {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });
    const data = await res.json();
    localStorage.setItem('access_token', data.access_token);
    if (data.token) setToken(data.token);
    else alert("Invalid credentials");
  }

  return (
    <div>
      <h2>Login</h2>
      <input value={username} onChange={e => setUsername(e.target.value)} placeholder="Username" /><br/>
      <input value={password} onChange={e => setPassword(e.target.value)} placeholder="Password" type="password" /><br/>
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}
