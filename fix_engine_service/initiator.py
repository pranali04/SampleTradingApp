import quickfix as fix
import quickfix44 as fix44
from message_handler import Application

if __name__ == "__main__":
    settings = fix.SessionSettings("quickfix.cfg")
    application = Application()
    storeFactory = fix.FileStoreFactory(settings)
    logFactory = fix.FileLogFactory(settings)
    initiator = fix.SocketInitiator(application, storeFactory, settings, logFactory)
    initiator.start()
    input("Press <ENTER> to stop\n")
    initiator.stop()
