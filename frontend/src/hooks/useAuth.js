import { useState } from 'react';

export default function useAuth() {
  const [token, setToken] = useState(localStorage.getItem('access_token') || null);

  const saveToken = (newToken) => {
    localStorage.setItem('access_token', newToken);
    setToken(newToken);
  };

  const logout = () => {
    localStorage.removeItem('access_token');
    setToken(null);
  };

  const authHeader = () => {
    return token ? { Authorization: `Bearer ${token}` } : {};
  };

  return { token, saveToken, logout, authHeader };
}
