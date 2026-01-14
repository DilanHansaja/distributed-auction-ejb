package com.dilanhansaja.ee.ejb.messages;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;

import javax.naming.InitialContext;
import java.util.Scanner;

@Startup
@Singleton
public class MessageSender {

    @Resource(lookup = "jms/MyConnectionFactory")
    private ConnectionFactory customFactory;

    @Resource(lookup = "jms/BidTopic")
    private Topic bidTopic;

    public void send(String msg) {

        try (JMSContext ctx = customFactory.createContext()) {
            ctx.createProducer().send(bidTopic, msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {
        try (JMSContext ctx = customFactory.createContext()) {
            ctx.createProducer().send(bidTopic, "Startup message");
        } catch (Exception e) {
            System.err.println("[JMSWarmUpBean] Failed to warm up JMS: " + e.getMessage());
        }
    }

}
