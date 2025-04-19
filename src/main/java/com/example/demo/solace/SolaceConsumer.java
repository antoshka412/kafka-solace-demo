package com.example.demo.solace;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

//@Component
public class SolaceConsumer {

    @JmsListener(destination = "${solace.queue-name}", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String message) {
        System.out.println("Received message from Solace queue: " + message);
    }
}