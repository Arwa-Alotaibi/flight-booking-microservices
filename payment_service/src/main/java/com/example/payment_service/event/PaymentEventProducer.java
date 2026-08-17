package com.example.payment_service.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PaymentEventProducer {
    private final KafkaTemplate<String,PaymentSuccessEvent> kafkaTemplate;
    public PaymentEventProducer(KafkaTemplate<String,PaymentSuccessEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendPaymentSuccessEvent(PaymentSuccessEvent paymentSuccessEvent){
        //(topic-name, event)
        kafkaTemplate.send("payment-success",paymentSuccessEvent);
    }
}
