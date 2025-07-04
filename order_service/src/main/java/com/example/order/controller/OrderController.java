package com.example.order.controller;

import com.example.order.dto.AmendOrderRequest;
import com.example.order.dto.NewOrderRequest;
import com.example.order.dto.CancelOrderRequest;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/new")
    public String newOrder(@RequestBody NewOrderRequest request) {
        return orderService.processNewOrder(request);
    }

    @PostMapping("/amend")
    public String amendOrder(@RequestBody AmendOrderRequest request) {
        return orderService.processAmendOrder(request);
    }

    @PostMapping("/cancel")
    public String cancelOrder(@RequestBody CancelOrderRequest request) {
        return orderService.processCancelOrder(request);
    }
}
