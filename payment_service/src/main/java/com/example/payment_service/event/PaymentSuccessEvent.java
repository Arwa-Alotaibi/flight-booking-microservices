package com.example.payment_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSuccessEvent {
    private Integer paymentId;
    private Integer bookingId;
    private BigDecimal amount;
    private String paymentReference;
}
