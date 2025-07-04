package com.example.notification.controller;

import com.example.notification.model.OrderStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/order-status")
    public void sendOrderStatus(@RequestBody OrderStatusMessage message) {
        messagingTemplate.convertAndSend("/topic/order-status", message);
    }
}
