package com.example.order.model;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderStatusMessage {
    private String orderId;
    private String status;
    private BigDecimal filledQty;
    private String side;
    private String symbol;
    private String execId;
    private String clOrdId;

    // Getters/setters
}
