package com.example.payment_service.service;

import com.example.payment_service.dto.PaymentRequestDto;
import com.example.payment_service.dto.PaymentResponseDto;
import com.example.payment_service.enums.PaymentStatus;
import com.example.payment_service.event.PaymentEventProducer;
import com.example.payment_service.event.PaymentSuccessEvent;
import com.example.payment_service.exception.HandleArgumentException;
import com.example.payment_service.exception.ResourceIdNotFoundException;
import com.example.payment_service.model.Payment;
import com.example.payment_service.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
@Service
public class PaymentServiceImpl implements PaymentService{
    private final ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    public PaymentServiceImpl(ModelMapper modelMapper,PaymentRepository paymentRepository,
                              PaymentEventProducer paymentEventProducer){
        this.modelMapper = modelMapper;
        this.paymentRepository = paymentRepository;
        this.paymentEventProducer = paymentEventProducer;
    }

    @Override
    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {
        boolean isBookingIdExists = paymentRepository.existsByBookingId(paymentRequestDto.getBookingId());
        if(isBookingIdExists){
            throw new HandleArgumentException("the booking id is already exists");
        }
        Payment payment = Payment.builder()
                .bookingId(paymentRequestDto.getBookingId())
                .paymentMethod(paymentRequestDto.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .paymentReference("PR"+UUID.randomUUID().toString().substring(0,6).toUpperCase())
                .amount(paymentRequestDto.getAmount())
                .build();
        Payment paymentSave = paymentRepository.save(payment);
        return modelMapper.map(paymentSave, PaymentResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentByBookingId(Long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId).orElseThrow(()->
                new ResourceIdNotFoundException("booking id not found"));
        return modelMapper.map(payment,PaymentResponseDto.class);

    }

    @Override
    @Transactional
    public PaymentResponseDto processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()->
                 new ResourceIdNotFoundException("payment id not found"));
        if(PaymentStatus.SUCCESS.equals(payment.getPaymentStatus())){
            throw new HandleArgumentException("the payment is already paid !");
        }
        if(PaymentStatus.FAILED.equals(payment.getPaymentStatus())){
            throw new HandleArgumentException("Cannot process a failed payment!");
        }
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        PaymentSuccessEvent paymentSuccessEvent =  PaymentSuccessEvent.builder()
                .bookingId(payment.getBookingId())
                .paymentId(payment.getPaymentId())
                .paymentReference(payment.getPaymentReference())
                .amount(payment.getAmount())
                .build();

        paymentEventProducer.sendPaymentSuccessEvent(paymentSuccessEvent);
        return modelMapper.map(payment,PaymentResponseDto.class);
    }
}
