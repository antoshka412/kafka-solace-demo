package com.example.demo.solace;

import com.example.demo.MessageProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SolaceProducer implements MessageProducer {

    private final JmsTemplate jmsTemplate;

    @Value("${solace.queue-name}")
    private String solaceQueue;

    public SolaceProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(solaceQueue, message);
        System.out.println("Message sent to Solace queue: " + message);
    }
}