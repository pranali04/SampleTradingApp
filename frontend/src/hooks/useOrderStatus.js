import { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export function useOrderStatus() {
  const [orderStatus, setOrderStatus] = useState([]);

  useEffect(() => {
    const socket = new SockJS('/ws'); // Proxy through API Gateway
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      stompClient.subscribe('/topic/order-status', (message) => {
        const statusUpdate = JSON.parse(message.body);
        setOrderStatus((prev) => [...prev, statusUpdate]);
      });
    });

    return () => {
      stompClient.disconnect();
    };
  }, []);

  return orderStatus;
}
