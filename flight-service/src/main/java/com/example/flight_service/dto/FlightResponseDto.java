package com.example.flight_service.dto;

import com.example.flight_service.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponseDto {
    private String flightNumber;

    private String departureAirportCode;

    private String arrivalAirportCode;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private BigDecimal price;

    private Integer availableSeats;

    private Integer totalSeats;

    private FlightStatus status;
}
