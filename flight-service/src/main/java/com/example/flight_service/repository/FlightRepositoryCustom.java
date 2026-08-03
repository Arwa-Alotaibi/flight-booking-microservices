package com.example.flight_service.repository;

import com.example.flight_service.dto.FlightResponseDto;
import com.example.flight_service.dto.FlightSearchRequest;
import com.example.flight_service.models.Flight;

import java.util.List;

public interface FlightRepositoryCustom {

    List<Flight> searchFlights(FlightSearchRequest flightSearchRequest);
    List<Flight> getCheapestFlight();
}
