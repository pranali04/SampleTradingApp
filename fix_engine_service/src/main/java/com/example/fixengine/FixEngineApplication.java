package com.example.fixengine;

import quickfix.FileStoreFactory;
import quickfix.LogFactory;
// import quickfix.MessageFactory;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.fix44.MessageFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import quickfix.*;
// import com.example.order.fix.FixMessageHandler;
import java.io.InputStream;

import com.example.fixengine.FixAppLogic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Configuration
public class FixEngineApplication {

    @Bean
    public SessionSettings sessionSettings() throws Exception {
        ClassPathResource cfg = new ClassPathResource("acceptor.cfg");
        try (InputStream is = cfg.getInputStream()) {
            return new SessionSettings(is);
        }
    }

    @Bean
    public MessageStoreFactory messageStoreFactory(SessionSettings settings) {
        return new FileStoreFactory(settings);
    }

    @Bean
    public LogFactory logFactory(SessionSettings settings) {
        return new FileLogFactory(settings);
    }

    @Bean
    public MessageFactory messageFactory() {
        return new quickfix.fix44.MessageFactory();
    }

   
    @Bean(initMethod = "start", destroyMethod = "stop")
    public SocketAcceptor acceptor(
            SessionSettings settings,
            MessageStoreFactory storeFactory,
            LogFactory logFactory,
            MessageFactory messageFactory,
            FixAppLogic application
    ) throws ConfigError {
        return new SocketAcceptor(
            application,
            storeFactory,
            settings,
            logFactory,
            messageFactory
        );
    

    // public static void main(String[] args) throws Exception {
    //     InputStream inputStream = FixEngineApplication.class.getResourceAsStream("acceptor.cfg");
    //     SessionSettings settings = new SessionSettings(inputStream);

    //     Application application = new FixAppLogic(); // your implementation
    //     FileStoreFactory storeFactory = new FileStoreFactory(settings);
    //     LogFactory logFactory = new quickfix.FileLogFactory(settings);
    //     MessageFactory messageFactory = new quickfix.fix44.MessageFactory();

    //     SocketAcceptor acceptor = new SocketAcceptor(application, storeFactory, settings, logFactory, messageFactory);

    //     acceptor.start();
    //     System.out.println("FIX Acceptor started. Press <enter> to stop.");
    //     System.in.read();
    //     acceptor.stop();
    // }
    }
    public static void main(String[] args) {
        SpringApplication.run(FixEngineApplication.class, args);
    }
}
