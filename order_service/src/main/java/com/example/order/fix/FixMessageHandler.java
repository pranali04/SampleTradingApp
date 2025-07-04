package com.example.order.fix;

import com.example.order.model.OrderEntity;
import com.example.order.model.OrderStatusMessage;
import com.example.order.service.NotificationService;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.*;

import quickfix.field.*;
import quickfix.fix44.ExecutionReport;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixMessageHandler extends MessageCracker implements Application {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final NotificationService notificationService;

    @Override
    public void fromAdmin(Message message, SessionID sessionId) {}
    @Override
    public void onCreate(SessionID sessionId) {}
    @Override
    public void onLogon(SessionID sessionId) {}
    @Override
    public void onLogout(SessionID sessionId) {}
    @Override
    public void toAdmin(Message message, SessionID sessionId) {}
    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {}
    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        crack(message, sessionId);
    }
   
    public void onMessage(ExecutionReport report, SessionID sessionID) throws FieldNotFound {
        String clOrdId = report.getClOrdID().getValue();
        String execType = report.getExecType().getValue() + "";
        String ordStatus = report.getOrdStatus().getValue() + "";
        double cumQty = report.getCumQty().getValue();

        log.info("Received ExecutionReport: ClOrdID={}, ExecType={}, Status={}, CumQty={}",
                clOrdId, execType, ordStatus, cumQty);

        OrderEntity order = orderRepository.findByClOrdId(clOrdId).orElseThrow();
        order.setFilledQty(BigDecimal.valueOf(cumQty));
        order.setStatus(mapOrdStatus(ordStatus));
        order.setExecId(report.getExecID().getValue());
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        OrderStatusMessage msg = new OrderStatusMessage();
        msg.setOrderId(order.getId().toString());
        msg.setClOrdId(order.getClOrdId());
        msg.setStatus(order.getStatus());
        msg.setFilledQty(order.getFilledQty());
        msg.setExecId(order.getExecId());
        // …any other fields you want to include…
        notificationService.sendOrderStatusUpdate(msg);

    }

    private String mapOrdStatus(String ordStatus) {
        switch (ordStatus) {
            case "0": return "NEW";
            case "1": return "PARTIALLY_FILLED";
            case "2": return "FILLED";
            case "4": return "CANCELED";
            case "5": return "REPLACED";
            default: return "UNKNOWN";
        }
    }
}
