package com.example.booking_service.model;


import com.example.booking_service.enums.BookingStatus;
import com.example.booking_service.enums.PaymentMethod;
import com.example.booking_service.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
@EntityListeners(AuditingEntityListener.class)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @Column(name="bookingReference", nullable = false,unique = true)
    private String bookingReference;

    @Column(name ="passengerId", nullable = false)
    private Integer passengerId;

    @Column(name="flightId", nullable = false)
    private Integer flightId;

    @Column(name ="totalPrice", nullable = false)
    private BigDecimal totalPrice;

    @Column(name="seatCount",nullable = false)
    private Integer seatCount;

    @Column(name="bookingStatus",nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Column(name = "bookingDate", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime bookingDate;

    @CreatedDate
    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;
}
