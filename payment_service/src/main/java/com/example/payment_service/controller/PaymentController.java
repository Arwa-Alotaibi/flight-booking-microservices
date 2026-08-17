package com.example.payment_service.controller;

import com.example.payment_service.dto.PaymentRequestDto;
import com.example.payment_service.dto.PaymentResponseDto;
import com.example.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto responseDto = paymentService.createPayment(paymentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{paymentId}/process")
    public ResponseEntity<PaymentResponseDto> processPayment(@PathVariable Long paymentId) {
        PaymentResponseDto responseDto = paymentService.processPayment(paymentId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentResponseDto> getPaymentByBookingId(@PathVariable Long bookingId) {
        PaymentResponseDto responseDto = paymentService.getPaymentByBookingId(bookingId);
        return ResponseEntity.ok(responseDto);
    }

}
