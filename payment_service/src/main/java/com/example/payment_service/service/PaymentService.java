package com.example.payment_service.service;

import com.example.payment_service.dto.PaymentRequestDto;
import com.example.payment_service.dto.PaymentResponseDto;
import org.springframework.stereotype.Service;


public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
    PaymentResponseDto getPaymentByBookingId(Long bookingId);
    PaymentResponseDto processPayment(Long paymentId);

}
