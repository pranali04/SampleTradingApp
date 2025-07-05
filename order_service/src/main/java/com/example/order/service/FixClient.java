package com.example.order.service;

import com.example.order.model.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.*;
import com.example.order.fix.FixInitiator;
import quickfix.SessionID;
import quickfix.Session;
import quickfix.SessionSettings;
import quickfix.SessionNotFound;
import quickfix.field.*;
import quickfix.fix44.NewOrderSingle;
import quickfix.fix44.OrderCancelReplaceRequest;
import quickfix.fix44.OrderCancelRequest;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
@Component
// @RequiredArgsConstructor
public class FixClient {

    private final FixInitiator fixInitiator;
    private final SessionSettings settings;
    private SessionID sessionID;

    public FixClient(FixInitiator fixInitiator,
                     SessionSettings settings) {
        this.fixInitiator = fixInitiator;
        this.settings = settings;
    }

    @PostConstruct
    public void init() {
        var iter = settings.sectionIterator();
        if (!iter.hasNext()) {
            throw new IllegalStateException("No FIX sessions defined in quickfixj.cfg");
        }
        this.sessionID = iter.next();
    }
    // @Autowired
    // public FixClient(FixInitiator fixInitiator, @Value("${fix.session.id}") String sessionIdStr) {
    //     this.fixInitiator = fixInitiator;
    //     String[] parts = sessionIdStr.split(":");
    //     this.sessionID = new SessionID(parts[0], parts[1], parts[2]);
    // }

    public void sendNewOrder(OrderEntity order) {
        try {
            NewOrderSingle newOrder = new NewOrderSingle(
                    new ClOrdID(order.getClOrdId()),
                    new Side(order.getSide().equalsIgnoreCase("BUY") ? Side.BUY : Side.SELL),
                    new TransactTime(),
                    new OrdType(order.getOrderType().equalsIgnoreCase("LIMIT") ? OrdType.LIMIT : OrdType.MARKET)
            );

            newOrder.set(new Symbol(order.getSymbol()));
            newOrder.set(new OrderQty(order.getQuantity()));
            newOrder.set(new Price(order.getPrice()));
            newOrder.set(new TimeInForce(TimeInForce.DAY));
            // Add client ID via NoPartyIDs repeating group
            quickfix.fix44.NewOrderSingle.NoPartyIDs party = new quickfix.fix44.NewOrderSingle.NoPartyIDs();
            party.set(new PartyID(order.getClientId()));
            party.set(new PartyRole(PartyRole.CLIENT_ID));

            newOrder.addGroup(party);

            // SessionID sessionID = settings.sectionIterator().next();
            Session.sendToTarget(newOrder, sessionID);
            // fixInitiator.send(newOrder, sessionID);
            log.info("FIX NewOrderSingle sent: {}", newOrder);
        } catch (Exception e) {
            log.error("Error sending NewOrderSingle FIX: ", e);
        }
    }

    public void sendAmend(OrderEntity order, String clOrdId, String origClOrdId) {
        try {
            OrderCancelReplaceRequest replaceRequest = new OrderCancelReplaceRequest(
                    new OrigClOrdID(origClOrdId),
                    new ClOrdID(clOrdId),
                    new Side(order.getSide().equalsIgnoreCase("BUY") ? Side.BUY : Side.SELL),
                    new TransactTime(),
                    new OrdType(order.getOrderType().equalsIgnoreCase("LIMIT") ? OrdType.LIMIT : OrdType.MARKET)
            );

            replaceRequest.set(new Symbol(order.getSymbol()));
            replaceRequest.set(new OrderQty(order.getQuantity()));
            replaceRequest.set(new Price(order.getPrice()));

            // SessionID sessionID = settings.sectionIterator().next();
            Session.sendToTarget(replaceRequest, sessionID);
            // fixInitiator.send(replaceRequest, sessionID);
            log.info("FIX OrderCancelReplaceRequest sent: {}", replaceRequest);
        } catch (Exception e) {
            log.error("Error sending OrderCancelReplaceRequest FIX: ", e);
        }
    }

    public void sendCancelOrder(OrderEntity order, String clOrdId, String origClOrdId) {
        try {
            OrderCancelRequest cancelRequest = new OrderCancelRequest(
                    new OrigClOrdID(origClOrdId),
                    new ClOrdID(clOrdId),
                    new Side(order.getSide().equalsIgnoreCase("BUY") ? Side.BUY : Side.SELL),
                    new TransactTime()
            );

            cancelRequest.set(new Symbol(order.getSymbol()));
            cancelRequest.set(new OrderQty(order.getQuantity()));

            // SessionID sessionID = settings.sectionIterator().next();
            Session.sendToTarget(cancelRequest, sessionID);
            // fixInitiator.send(cancelRequest, sessionID);
            log.info("FIX OrderCancelRequest sent: {}", cancelRequest);
        } catch (Exception e) {
            log.error("Error sending OrderCancelRequest FIX: ", e);
        }
    }
}
