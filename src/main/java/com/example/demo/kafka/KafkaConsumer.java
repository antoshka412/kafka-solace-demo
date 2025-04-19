package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "${kafka.topic-name}", groupId = "group_id")
    public void consumeMessage(String message) {
        System.out.println("Message received from Kafka: " + message);
    }
}