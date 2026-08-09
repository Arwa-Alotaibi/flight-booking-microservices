package com.example.booking_service.dto;

import com.example.booking_service.enums.BookingStatus;
import com.example.booking_service.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDto {
    private String bookingReference;
    private BigDecimal totalPrice;
    private Integer seatCount;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime bookingDate;
    private Integer page = 0;
    private Integer size = 10;

}
