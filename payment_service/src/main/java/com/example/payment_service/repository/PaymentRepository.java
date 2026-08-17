package com.example.payment_service.repository;

import com.example.payment_service.dto.PaymentResponseDto;
import com.example.payment_service.enums.PaymentStatus;
import com.example.payment_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
    boolean existsByBookingIdAndPaymentStatus(Long bookingId , PaymentStatus paymentStatus );
    Optional<Payment> findByBookingId(Long bookingId );
    boolean existsByBookingId(Long bookingId);




}
