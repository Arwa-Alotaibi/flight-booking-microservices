package com.example.booking_service.client;

import com.example.booking_service.dto.PaymentRequestDto;
import com.example.booking_service.dto.PaymentResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/api/v1/payment")
    PaymentResponseDto createPayment(@Valid @RequestBody PaymentRequestDto paymentRequestDto);

    @PutMapping("/api/v1/payment/{bookingId}/process")
    PaymentResponseDto processPayment(@PathVariable Integer bookingId);

    @GetMapping("/api/v1/payment//booking/{bookingId}")
   PaymentResponseDto getPaymentByBookingId(@PathVariable Integer bookingId) ;

}
