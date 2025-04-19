package com.example.demo.solace;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;
import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;


@Configuration
public class SolaceConfig {

    @Value("${solace.java.host}")
    private String solaceHost;

    @Value("${solace.java.msgVpn}")
    private String solaceVPN;

    @Value("${solace.java.clientUsername}")
    private String solaceUsername;

    @Value("${solace.java.clientPassword}")
    private String solacePassword;

    @Value("${solace.queue-name}")
    private String solaceQueueName;

    /**
     * Creates a Solace connection factory using Solace JMS API
     *
     * @return a Solace ConnectionFactory
     * @throws Exception if an error occurs during creation
     */
    @Bean
    public ConnectionFactory solaceConnectionFactory() throws Exception {
        SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();
        connectionFactory.setHost(solaceHost);
        connectionFactory.setVPN(solaceVPN);
        connectionFactory.setUsername(solaceUsername);
        connectionFactory.setPassword(solacePassword);

        return connectionFactory;
    }

    /**
     * Creates a JmsTemplate for interacting with Solace
     *
     * @param connectionFactory the Solace connection factory
     * @return JmsTemplate
     */
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestinationName(solaceQueueName); // Make sure this name matches exactly
        return jmsTemplate;
    }

    /**
     * Configures the JMS listener container factory for receiving messages
     *
     * @param connectionFactory the Solace connection factory
     * @return DefaultJmsListenerContainerFactory
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("1-5"); // Specify concurrent consumers (adjust as needed)
        factory.setPubSubDomain(false); // Set to true for topics, false for queues
        return factory;
    }
}