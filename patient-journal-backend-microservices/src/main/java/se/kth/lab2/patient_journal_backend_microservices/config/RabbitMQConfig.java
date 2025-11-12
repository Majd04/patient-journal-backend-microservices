package se.kth.lab2.patient_journal_backend_microservices.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "patient_journal_exchange";
    public static final String ROUTING_KEY_PATIENT = "patient.events";
    public static final String ROUTING_KEY_JOURNAL = "journal.events";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
}