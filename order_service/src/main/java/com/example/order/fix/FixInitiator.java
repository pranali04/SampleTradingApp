package com.example.order.fix;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixInitiator {

    private final FixMessageHandler handler;

    private SocketInitiator initiator;

    // public FixInitiator(FixMessageHandler handler) {
    //     this.handler = handler;
    // }

    @PostConstruct
    public void start() throws ConfigError {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("src/main/resources/quickfixj.cfg");
        SessionSettings settings = new SessionSettings(inputStream);

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();
        // FixMessageHandler application = new FixMessageHandler();
        this.initiator = new SocketInitiator(handler, storeFactory, settings, logFactory, messageFactory);
        initiator.start();

        log.info("QuickFIX/J Initiator started");
    }

    @PreDestroy
    public void stop() {
        if (initiator != null) {
            initiator.stop();
            log.info("QuickFIX/J Initiator stopped");
        }
    }

    // public void send(Message msg, SessionID sessionID) throws SessionNotFound {
    //     initiator.send(msg, sessionID);
    // }
}
