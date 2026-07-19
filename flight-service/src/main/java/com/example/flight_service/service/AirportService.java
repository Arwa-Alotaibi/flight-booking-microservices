package com.example.flight_service.service;

import com.example.flight_service.models.Airport;
import org.springframework.stereotype.Service;

@Service
public interface AirportService {

    Airport getAirport(Integer airportId);

    String getAirportCode(Airport airport);
}
