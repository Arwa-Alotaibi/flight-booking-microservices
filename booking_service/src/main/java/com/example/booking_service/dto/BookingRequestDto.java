package com.example.booking_service.dto;

import com.example.booking_service.enums.PaymentMethod;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequestDto {

    @NotNull(message = "Passenger ID is required")
    private Integer passengerId;

    @NotNull(message = "Flight ID is required")
    private Integer flightId;

    @NotNull(message = "Seat count is required")
    @Min(value = 1, message = "Seat count must be at least 1")
    @Max(value = 9, message = "Seat count cannot exceed 9")
    private Integer seatCount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

}
