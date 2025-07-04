package com.example.order.service;

import com.example.order.dto.NewOrderRequest;
import com.example.order.dto.AmendOrderRequest;
import com.example.order.dto.CancelOrderRequest;
import com.example.order.model.OrderEntity;
import com.example.order.model.OrderStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.order.service.FixClient;
import com.example.order.repository.OrderRepository;
import java.math.BigDecimal;
@Service
public class OrderService {

    @Autowired
    private FixClient fixClient;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OrderRepository orderRepository;

    public OrderService(FixClient fixClient, OrderRepository orderRepository) {
      this.fixClient = fixClient;
      this.orderRepository = orderRepository;
   }

    public String processNewOrder(NewOrderRequest request) {
        // 1. Save to DB
        OrderEntity order = new OrderEntity(request);
        order.setStatus("NEW");
        orderRepository.save(order);

        // 2. Send FIX
        fixClient.sendNewOrder(order);

        // 3. Notify
        OrderStatusMessage msg = new OrderStatusMessage();
        msg.setOrderId(order.getId().toString());
        msg.setStatus("NEW");
        msg.setSymbol(order.getSymbol());
        msg.setSide(order.getSide());
        msg.setFilledQty(BigDecimal.ZERO);
        notificationService.sendOrderStatusUpdate(msg);

        return "New Order Placed";
    }


    public String processAmendOrder(AmendOrderRequest request) {
        // 1. Find & Update in DB
        OrderEntity order = orderRepository.findByClOrdId(request.getOrigClOrdId())
                                       .orElseThrow(() -> new RuntimeException("Original order not found"));
        // Create a new ClOrdID for FIX
        String newClOrdId = request.getClOrdId();

        order.setQuantity(request.getQty());
        order.setPrice(request.getPrice());
        order.setStatus("AMENDED");
        orderRepository.save(order);

        // Send to FIX
        fixClient.sendAmend(order, newClOrdId, request.getOrigClOrdId());

        // 3. Notify
        OrderStatusMessage msg = new OrderStatusMessage();
        msg.setOrderId(order.getId().toString());
        msg.setStatus("AMENDED");
        msg.setSymbol(order.getSymbol());
        msg.setSide(order.getSide());
        msg.setFilledQty(order.getFilledQty());
        notificationService.sendOrderStatusUpdate(msg);

        return "Order Amended";
    }

    public String processCancelOrder(CancelOrderRequest request) {
         OrderEntity order = orderRepository.findByClOrdId(request.getOrigClOrdId())
                                       .orElseThrow(() -> new RuntimeException("Original order not found"));

        order.setStatus("CANCELED");
        orderRepository.save(order);

        fixClient.sendCancelOrder(order, request.getClOrdId(), request.getOrigClOrdId());

        OrderStatusMessage msg = new OrderStatusMessage();
        msg.setOrderId(order.getId().toString());
        msg.setStatus("CANCELED");
        msg.setSymbol(order.getSymbol());
        msg.setSide(order.getSide());
        msg.setFilledQty(order.getFilledQty());
        notificationService.sendOrderStatusUpdate(msg);

        return "Order Canceled";
    }
}
