package com.example.payment_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.example.payment_service.dto.PaymentRequestDto;
import com.example.payment_service.dto.PaymentResponseDto;
import com.example.payment_service.enums.PaymentMethod;
import com.example.payment_service.enums.PaymentStatus;
import com.example.payment_service.event.PaymentEventProducer;
import com.example.payment_service.event.PaymentSuccessEvent;
import com.example.payment_service.exception.HandleArgumentException;
import com.example.payment_service.exception.ResourceIdNotFoundException;
import com.example.payment_service.model.Payment;
import com.example.payment_service.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentEventProducer paymentEventProducer;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    public void shouldCreatePaymentSuccessfully() {
        PaymentRequestDto paymentRequestDto = buildPaymentRequest();
        Payment payment = buildPayment();
        PaymentResponseDto responseDto = buildPaymentResponse();
        when(paymentRepository.existsByBookingId(paymentRequestDto.getBookingId()))
                .thenReturn(false);
        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);
        when(modelMapper.map(any(Payment.class), eq(PaymentResponseDto.class)))
                .thenReturn(responseDto);

        PaymentResponseDto result = paymentService.createPayment(paymentRequestDto);

        assertEquals(PaymentStatus.UNPAID, payment.getPaymentStatus());
        assertEquals(new BigDecimal("460.50"), result.getAmount());

        verify(paymentRepository).existsByBookingId(paymentRequestDto.getBookingId());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    public void shouldReturnPaymentWhenBookingIdExists(){
        Payment payment = buildPayment();
        PaymentResponseDto responseDto = buildPaymentResponse();
        when(paymentRepository.findByBookingId(payment.getBookingId()))
                .thenReturn(Optional.of(payment));

        when(modelMapper.map(payment, PaymentResponseDto.class))
                .thenReturn(responseDto);

        PaymentResponseDto paymentResponseDto = paymentService.
                getPaymentByBookingId(payment.getBookingId());

        assertNotNull(paymentResponseDto);
        assertEquals(payment.getBookingId(), paymentResponseDto.getBookingId());
        assertEquals(PaymentStatus.PAID, paymentResponseDto.getPaymentStatus());
        verify(paymentRepository).findByBookingId(payment.getBookingId());
    }

    @Test
    void shouldThrowExceptionWhenPaymentByBookingIdDoesNotExist(){
        Payment payment = buildPayment();
        when(paymentRepository.findByBookingId(payment.getBookingId()))
                .thenReturn(Optional.empty());

        ResourceIdNotFoundException exception = assertThrows(
                ResourceIdNotFoundException.class,
                ()-> paymentService.getPaymentByBookingId(payment.getBookingId())
        );

        assertEquals("booking id not found", exception.getMessage());

    }


    @Test
    void shouldFailWhenPaymentAlreadyExistsForBooking(){
        PaymentRequestDto paymentRequestDto = buildPaymentRequest();
        when(paymentRepository.existsByBookingId(paymentRequestDto.getBookingId()))
                .thenReturn(true);

        HandleArgumentException exception = assertThrows(
                HandleArgumentException.class,
                ()-> paymentService.createPayment(paymentRequestDto)
        );
        assertEquals("the booking id is already exists", exception.getMessage());
        verify(paymentRepository, never()).save(any(Payment.class));
    }
    @Test
    public void shouldProcessPaymentSuccessfully(){
        Payment payment = buildPayment();
        when(paymentRepository.findByBookingId(payment.getBookingId()))
                .thenReturn(Optional.of(payment));
        PaymentResponseDto responseDto = buildPaymentResponse();

        when(modelMapper.map(any(Payment.class), eq(PaymentResponseDto.class)))
                .thenReturn(responseDto);

        PaymentResponseDto result = paymentService.processPayment(payment.getBookingId());
        assertEquals(PaymentStatus.PAID, payment.getPaymentStatus());
        verify(paymentRepository).findByBookingId(payment.getBookingId());
        verify(paymentEventProducer).sendPaymentSuccessEvent(any(PaymentSuccessEvent.class));
    }

    @Test
    void shouldFailWhenPaymentIsAlreadyPaid(){
        Payment payment = buildPayment();
        payment.setPaymentStatus(PaymentStatus.PAID);
        when(paymentRepository.findByBookingId(payment.getBookingId()))
                .thenReturn(Optional.of(payment));

        HandleArgumentException exception = assertThrows(
                HandleArgumentException.class,
                ()-> paymentService.processPayment(payment.getBookingId())
        );
        assertEquals("the payment is already paid !", exception.getMessage());
        verify(paymentEventProducer, never()).sendPaymentSuccessEvent(any(PaymentSuccessEvent.class));
    }

    @Test
    void shouldFailWhenPaymentDoesNotExistDuringProcessing(){
        Payment payment = buildPayment();

        when(paymentRepository.findByBookingId(payment.getBookingId()))
                .thenReturn(Optional.empty());

        ResourceIdNotFoundException exception = assertThrows(
                ResourceIdNotFoundException.class,
                ()-> paymentService.processPayment(payment.getBookingId())
        );
        assertEquals("booking id not found", exception.getMessage());
        verify(paymentEventProducer, never()).sendPaymentSuccessEvent(any(PaymentSuccessEvent.class));
    }

    @Test
    void shouldFailWhenPaymentIsRefunded(){
        Payment payment = buildPayment();
        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        when(paymentRepository.findByBookingId(payment.getBookingId()))
                .thenReturn(Optional.of(payment));

        HandleArgumentException handleArgumentException  = assertThrows(
                HandleArgumentException.class,
                ()-> paymentService.processPayment(payment.getBookingId())
        );
        assertEquals("Cannot process a refunded payment.", handleArgumentException.getMessage());
        verify(paymentEventProducer, never()).sendPaymentSuccessEvent(any(PaymentSuccessEvent.class));
    }
    public Payment buildPayment() {
        return Payment.builder()
                .paymentId(1)
                .bookingId(10)
                .paymentMethod(PaymentMethod.MADA)
                .paymentStatus(PaymentStatus.UNPAID)
                .paymentReference("PR" + UUID.randomUUID().toString().substring(0, 6).toUpperCase())
                .amount(new BigDecimal("460.50"))
                .build();
    }

    public PaymentRequestDto buildPaymentRequest() {
        return PaymentRequestDto.builder()
                .bookingId(10)
                .amount(new BigDecimal("460.50"))
                .paymentMethod(PaymentMethod.MADA)
                .build();
    }

    public PaymentResponseDto buildPaymentResponse() {
        return PaymentResponseDto.builder()
                .paymentId(1)
                .bookingId(10)
                .amount(new BigDecimal("460.50"))
                .paymentMethod(PaymentMethod.MADA)
                .paymentStatus(PaymentStatus.PAID)
                .build();
    }


}