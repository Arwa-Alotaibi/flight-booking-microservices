package com.example.notification_service.consumer;

import com.example.notification_service.event.PaymentSuccessEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class PaymentEventConsumer {

    private ObjectMapper objectMapper;

    public PaymentEventConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    @KafkaListener(topics = "payment-success",
            groupId = "notification-group")
    public void consume(String json){
        PaymentSuccessEvent paymentSuccessEvent = objectMapper.readValue(json, PaymentSuccessEvent.class);
        System.out.println("payment success >>>"
        +"bookingId=: "+paymentSuccessEvent.getBookingId()
        +" paymentId= : "+paymentSuccessEvent.getPaymentId()
        +" amount= : "+paymentSuccessEvent.getAmount());

    }
}
