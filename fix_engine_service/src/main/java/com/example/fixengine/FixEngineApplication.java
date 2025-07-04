package com.example.fixengine;

import quickfix.Application;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
// import quickfix.MessageFactory;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.fix44.MessageFactory;

import java.io.InputStream;

public class FixEngineApplication {

    public static void main(String[] args) throws Exception {
        InputStream inputStream = FixEngineApplication.class.getResourceAsStream("src/main/resources/acceptor.cfg");
        SessionSettings settings = new SessionSettings(inputStream);

        Application application = new FixAppLogic(); // your implementation
        FileStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new quickfix.FileLogFactory(settings);
        MessageFactory messageFactory = new quickfix.fix44.MessageFactory();

        SocketAcceptor acceptor = new SocketAcceptor(application, storeFactory, settings, logFactory, messageFactory);

        acceptor.start();
        System.out.println("FIX Acceptor started. Press <enter> to stop.");
        System.in.read();
        acceptor.stop();
    }
}
