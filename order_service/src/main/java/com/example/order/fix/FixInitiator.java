package com.example.order.fix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import quickfix.*;
import com.example.order.fix.FixMessageHandler;
import java.io.InputStream;

@Configuration
public class FixInitiator {

    /**
     * Load quickfixj.cfg from src/main/resources and expose as a Spring bean.
     */
    @Bean
    public SessionSettings sessionSettings() throws Exception {
        ClassPathResource cfg = new ClassPathResource("quickfixj.cfg");
        try (InputStream is = cfg.getInputStream()) {
            return new SessionSettings(is);
        }
    }

    /**
     * File-based message store factory.
     */
    @Bean
    public MessageStoreFactory messageStoreFactory(SessionSettings settings) {
        return new FileStoreFactory(settings);
    }

    /**
     * File-based log factory.
     */
    @Bean
    public LogFactory logFactory(SessionSettings settings) {
        return new FileLogFactory(settings);
    }

    /**
     * Default FIX message factory.
     */
    @Bean
    public MessageFactory messageFactory() {
        return new DefaultMessageFactory();
    }

    /**
     * Your Application implementation (OrderFixApp) needs to be a bean too.
     */
    // @Bean
    // public Application fixApplication(FixMessageHandler handler) {
    //     return new FixMessageHandler(handler);
    // }

    /**
     * SocketInitiator as a bean—starts at init, stops at shutdown.
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Initiator socketInitiator(
            SessionSettings settings,
            MessageStoreFactory storeFactory,
            LogFactory logFactory,
            MessageFactory messageFactory,
            FixMessageHandler application
    ) throws ConfigError {
        return new SocketInitiator(
            application,
            storeFactory,
            settings,
            logFactory,
            messageFactory
        );
    }
}
