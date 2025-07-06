package com.example.fixengine;

import quickfix.*;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.NewOrderSingle;
import quickfix.fix44.OrderCancelReplaceRequest;
import quickfix.fix44.OrderCancelRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Slf4j
@Component
@RequiredArgsConstructor
public class FixAppLogic extends MessageCracker implements Application {

    @Override
    public void onCreate(SessionID sessionID) {
        System.out.println("Session created: " + sessionID);
    }

    @Override
    public void onLogon(SessionID sessionID) {
        System.out.println("Logon: " + sessionID);
    }

    @Override
    public void onLogout(SessionID sessionID) {
        System.out.println("Logout: " + sessionID);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) { }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) { }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend { }

    @Override
    public void fromApp(Message message, SessionID sessionID)
            throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("Received App Message: " + message);
        crack(message, sessionID);
    }

    // === Handle New Order Single ===
    public void onMessage(NewOrderSingle order, SessionID sessionID)
            throws FieldNotFound, SessionNotFound {
        System.out.println("Handling NewOrderSingle...");

        String clOrdID = order.getClOrdID().getValue();
        String symbol = order.getSymbol().getValue();
        char side = order.getSide().getValue();
        double qty = order.getOrderQty().getValue();
        double price = order.isSetPrice() ? order.getPrice().getValue() : 0.0;

        System.out.printf("New Order: %s %s %s %f @ %f%n",
                clOrdID, symbol, side, qty, price);

        ExecutionReport execReport = buildExecutionReport(
                order, clOrdID, OrdStatus.NEW, "New order accepted", qty, 0);

        Session.sendToTarget(execReport, sessionID);
    }

    // === Handle Amend Order ===
    public void onMessage(OrderCancelReplaceRequest amend, SessionID sessionID)
            throws FieldNotFound, SessionNotFound {
        System.out.println("Handling OrderCancelReplaceRequest (Amend)...");

        String clOrdID = amend.getClOrdID().getValue();
        String origClOrdID = amend.getOrigClOrdID().getValue();
        double newQty = amend.getOrderQty().getValue();
        double newPrice = amend.isSetPrice() ? amend.getPrice().getValue() : 0.0;

        System.out.printf("Amend Order: OrigID=%s NewID=%s NewQty=%f NewPrice=%f%n",
                origClOrdID, clOrdID, newQty, newPrice);

        ExecutionReport execReport = buildExecutionReport(
                amend, clOrdID, OrdStatus.REPLACED, "Order amended", newQty, 0);

        Session.sendToTarget(execReport, sessionID);
    }

    // === Handle Cancel Order ===
    public void onMessage(OrderCancelRequest cancel, SessionID sessionID)
            throws FieldNotFound, SessionNotFound {
        System.out.println("Handling OrderCancelRequest...");

        String clOrdID = cancel.getClOrdID().getValue();
        String origClOrdID = cancel.getOrigClOrdID().getValue();
        System.out.printf("Cancel Order: OrigID=%s CancelID=%s%n",
                origClOrdID, clOrdID);

        ExecutionReport execReport = buildExecutionReport(
                cancel, clOrdID, OrdStatus.CANCELED, "Order canceled", 0, 0);

        Session.sendToTarget(execReport, sessionID);
    }

    // === Common ExecutionReport Builder ===
private ExecutionReport buildExecutionReport(
        Message order, String clOrdID,
        char ordStatus, String text,
        double leavesQty, double cumQty) throws FieldNotFound {

    String execID = UUID.randomUUID().toString();

    Symbol symbol = order.isSetField(Symbol.FIELD)
            ? new Symbol(order.getString(Symbol.FIELD))
            : new Symbol("UNKNOWN");

    Side side = order.isSetField(Side.FIELD)
            ? new Side(order.getChar(Side.FIELD))
            : new Side(Side.BUY);

    LeavesQty leavesQtyField = new LeavesQty(leavesQty);
    CumQty cumQtyField = new CumQty(cumQty);
    AvgPx avgPx = new AvgPx(0); // Average price

    ExecutionReport execReport = new ExecutionReport(
            new OrderID(UUID.randomUUID().toString()),
            new ExecID(execID),
            new ExecType(ordStatus),
            new OrdStatus(ordStatus),
            side,
            leavesQtyField,
            cumQtyField,
            avgPx
    );

    execReport.set(new ClOrdID(clOrdID));
    execReport.set(symbol);

    if (order.isSetField(OrderQty.FIELD)) {
        execReport.set(new OrderQty(order.getDouble(OrderQty.FIELD)));
    }

    execReport.set(new Text(text));

    // Optional: last fill qty/px if needed
    execReport.set(new LastQty(0));
    execReport.set(new LastPx(0));

    return execReport;
}
}
