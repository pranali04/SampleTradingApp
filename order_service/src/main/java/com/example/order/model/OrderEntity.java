package com.example.order.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.example.order.dto.NewOrderRequest;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cl_ord_id", unique = true)
    private String clOrdId;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "exec_id")
    private String execId;

    private String symbol;
    private String side;
    private double quantity;
    private double price;
    private String orderType;
    private String clientId;
    private String timeInForce;
    private String venue;
    private String customTags;
    private String status;
    private BigDecimal filledQty;

    // === Constructors ===

    public OrderEntity() {}

    public OrderEntity(NewOrderRequest request) {
        this.clOrdId = request.getClOrdId();
        this.symbol = request.getSymbol();
        this.side = request.getSide();
        this.quantity = request.getQty();
        this.price = request.getPrice();
        this.orderType = request.getOrderType();
        this.clientId = request.getClientId();
        this.timeInForce = request.getTimeInForce();
        this.venue = request.getVenue();
        this.customTags = request.getCustomTags();
        this.status = "NEW";
        this.filledQty = BigDecimal.ZERO;
    }

    // === Getters & Setters ===

    public Long getId() { return id; }
    public String getClOrdId() { return clOrdId; }
    public void setClOrdId(String clOrdId) { this.clOrdId = clOrdId; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getSide() { return side; }
    public void setSide(String side) { this.side = side; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getTimeInForce() { return timeInForce; }
    public void setTimeInForce(String timeInForce) { this.timeInForce = timeInForce; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getCustomTags() { return customTags; }
    public void setCustomTags(String customTags) { this.customTags = customTags; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getFilledQty() { return filledQty; }
    public void setFilledQty(BigDecimal filledQty) { this.filledQty = filledQty; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getExecId() {return execId;}
    public void setExecId(String execId) {this.execId = execId;}

}
