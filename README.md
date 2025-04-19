# Getting Started
This is a small demo project to try sending messages to Kafka / Solace.

### To run the app

1. Run the docker compose file  
``docker-compose up``
2. Specify the message broker to be used in event.broker of application.properties
3. Create the queue in the chosen message broker and set it name in application.properties.
4. Run the app as a Spring boot app  
``mvn spring-boot:run``
5. Send a message to a chosen broker, for example Kafka 
``
curl -X POST "http://localhost:12345/api/publish?message=HelloKafka"
``
