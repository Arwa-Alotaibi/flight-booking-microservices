package com.example.flight_service.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequestDto {
    @NotBlank
    private String  flightNumber;

    @NotNull
    private Integer  departureAirportId;

    @NotNull
    private Integer  arrivalAirportId;

    @NotNull
    @Future
    private LocalDateTime departureTime;

    @NotNull
    @Future
    private LocalDateTime arrivalTime;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private Integer totalSeats;


}
