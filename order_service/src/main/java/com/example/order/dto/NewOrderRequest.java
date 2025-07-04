package com.example.order.dto;

import lombok.Data;

@Data
public class NewOrderRequest {
    private String clOrdId;
    private String symbol;
    private String side;
    private double qty;
    private double price;
    private String orderType;
    private String clientId;
    private String timeInForce;
    private String venue;
    private String customTags;
}
