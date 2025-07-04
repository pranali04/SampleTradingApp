package com.example.order.service;

import com.example.order.model.OrderStatusMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Value("${notification.service.url}")
    private String notificationUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendOrderStatusUpdate(OrderStatusMessage message) {
        restTemplate.postForEntity(notificationUrl, message, Void.class);
    }
}
