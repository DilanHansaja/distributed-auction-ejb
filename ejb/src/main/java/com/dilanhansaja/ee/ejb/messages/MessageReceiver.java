package com.dilanhansaja.ee.ejb.messages;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import com.dilanhansaja.ee.ejb.socket.BidSocket;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",propertyValue = "jakarta.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destinationLookup",propertyValue = "jms/BidTopic")
})
public class MessageReceiver implements MessageListener {

    @Override
    public void onMessage(Message message) {

        try {
            System.out.println(message.getBody(String.class));
            BidSocket.broadcast(message.getBody(String.class));
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
