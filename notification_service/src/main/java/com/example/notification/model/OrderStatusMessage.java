package com.example.notification.model;

public class OrderStatusMessage {
    private String orderId;
    private String status;
    private int filledQty;
    private String side;
    private String symbol;

    // Add other fields as needed
    // Getters and setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFilledQty() {
        return filledQty;
    }

    public void setFilledQty(int filledQty) {
        this.filledQty = filledQty;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
