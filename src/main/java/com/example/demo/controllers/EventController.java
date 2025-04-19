package com.example.demo.controllers;

import com.example.demo.MessageProducer;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.solace.SolaceProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class EventController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private SolaceProducer solaceProducer;

    @Value("${event.broker}")
    private String eventBroker;

    // Send message to MQ
    @PostMapping("/publish")
    public String publishMessage(@RequestParam("message") String message) {
        // Define a Map to associate brokers with their corresponding producers
        Map<String, MessageProducer> brokerMap = Map.of(
                "kafka", kafkaProducer,
                "solace", solaceProducer
        );

        // Retrieve the producer for the selected broker
        MessageProducer producer = brokerMap.get(eventBroker);

        if (producer != null) {
            producer.sendMessage(message);
            return String.format("Message sent to %s successfully!", eventBroker);
        } else {
            return "No Broker Selected!";
        }
    }

}