package com.example.flight_service.dto;

import com.example.flight_service.enums.FlightStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FlightSearchRequest {
    private String departureAirportCode;

    private String arrivalAirportCode;

    private FlightStatus status;

    private LocalDate departureDate;

    private BigDecimal maxPrice;
}
