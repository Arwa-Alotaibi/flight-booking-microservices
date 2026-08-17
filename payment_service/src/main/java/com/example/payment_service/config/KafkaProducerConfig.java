package com.example.payment_service.config;

import com.example.payment_service.event.PaymentSuccessEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public KafkaTemplate<String, PaymentSuccessEvent> kafkaTemplate(
            ProducerFactory<String, PaymentSuccessEvent> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
}
