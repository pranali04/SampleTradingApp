import { useEffect } from 'react';

export default function useOrderStatus(setStatus) {
  useEffect(() => {
    const ws = new WebSocket("ws://localhost:8004/ws/notifications");
    ws.onopen = () => console.log("Notification WS connected");
    ws.onmessage = (event) => {
      const data = JSON.parse(event.data);
      console.log("Notification:", data);
      setStatus(data);
    };
    return () => ws.close();
  }, [setStatus]);
}
