package com.example.order.dto;

import lombok.Data;

@Data
public class AmendOrderRequest {
    private String origClOrdId;  // Original ClOrdID
    private String clOrdId;      // New ClOrdID for the amendment
    private double qty;          // New quantity
    private double price;        // New price
    private String timeInForce;  // Optional
    private String customTags;   // Optional
}
