package com.example.flight_service.dto;

import com.example.flight_service.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchRequest {
    private String departureAirportCode;
    private String arrivalAirportCode;
    private FlightStatus status;
    private LocalDate departureDate;
    private BigDecimal maxPrice;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy;
    private String direction;
}
