import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import MarketDashboard from './components/MarketDashboard';
import OrderForm from './components/OrderForm';
import OrderStatus from './components/OrderStatus';

function App() {
  return (
    <Router>
      <Routes>
        {/* Redirect root to /login */}
        <Route path="/" element={<Navigate to="/login" replace />} />

        {/* Your existing routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<MarketDashboard />} />
        <Route path="/order" element={<OrderForm />} />
        <Route path="/status" element={<OrderStatus />} />

        {/* Catch‑all: also redirect any unknown path to /login */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </Router>
  );
}

export default App;
