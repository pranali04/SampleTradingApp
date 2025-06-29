import quickfix as fix
import quickfix44 as fix44

class Application(fix.Application):
    def onCreate(self, sessionID): pass
    def onLogon(self, sessionID): print("Logon -", sessionID)
    def onLogout(self, sessionID): print("Logout -", sessionID)

    def toAdmin(self, message, sessionID): pass
    def fromAdmin(self, message, sessionID): pass

    def toApp(self, message, sessionID): pass
    def fromApp(self, message, sessionID): print("Received:", message)

    def sendNewOrderSingle(self, order):
        message = fix44.NewOrderSingle()
        message.setField(fix.ClOrdID(str(order["id"])))
        message.setField(fix.HandlInst(fix.HandlInst_MANUAL_ORDER_BEST_EXECUTION))
        message.setField(fix.Symbol(order["symbol"]))
        message.setField(fix.Side(fix.Side_BUY if order["side"] == "BUY" else fix.Side_SELL))
        message.setField(fix.TransactTime())
        message.setField(fix.OrdType(fix.OrdType_LIMIT))
        message.setField(fix.Price(order["price"]))
        message.setField(fix.OrderQty(order["quantity"]))

        fix.Session.sendToTarget(message, fix.Session.lookupSession(fix.SessionID(
            "FIX.4.2", "MOCKTRADER", "FIXIMULATOR")))
