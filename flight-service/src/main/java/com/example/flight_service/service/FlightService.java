package com.example.flight_service.service;
import com.example.flight_service.dto.FlightRequestDto;
import com.example.flight_service.dto.FlightResponseDto;
import com.example.flight_service.dto.FlightSearchRequest;
import com.example.flight_service.dto.FlightUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface FlightService {

    FlightResponseDto addFlight(FlightRequestDto flightRequestDto);
    FlightResponseDto updateFlight(Integer flightId , FlightUpdateDto flightUpdateDto);
    void deleteFlight(Integer flightId);
    FlightResponseDto getFlightByFlightNumber(String flightNumber);
    List<FlightResponseDto> getAllFlights();
    List<FlightResponseDto> getFlightsWithAvailableSeats();
    List<FlightResponseDto> searchFlight(FlightSearchRequest flightSearchRequest);
    void updateAvailableSeats(Integer flightId, Integer seatsCount);
    void releaseSeats(Integer flightId , Integer seatsCount);


}
