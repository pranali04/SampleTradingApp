package com.example.order.dto;

import lombok.Data;

@Data
public class CancelOrderRequest {
    private String origClOrdId;  // Original ClOrdID to cancel
    private String clOrdId;      // New ClOrdID for the cancel request
    private String reason;       // Optional reason for cancel
}
