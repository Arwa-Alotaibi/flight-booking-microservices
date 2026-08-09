package com.example.booking_service.dto;

import com.example.booking_service.enums.BookingStatus;
import com.example.booking_service.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingSearchRequest {
    private Integer passengerId;
    private Integer flightId;
    private String bookingReference;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer page = 0;
    private Integer size = 10;


}
