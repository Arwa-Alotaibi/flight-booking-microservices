package com.example.booking_service.client;


import com.example.booking_service.dto.FlightResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name="flight-service",url="http://localhost:8083")
public interface FlightClient {
    @GetMapping("/api/v1/flights/id/{flightId}")
    FlightResponseDto getFlightByFlightId(@PathVariable Integer flightId);

    @PutMapping("/api/v1/flights/update/seats/{flightId}/{seatCount}")
    void updateAvailableSeats(@PathVariable Integer flightId,
                              @PathVariable Integer seatCount);

    @PutMapping("/api/v1/flights/release/seats/{flightId}/{seatCount}")
    void releaseSeats(@PathVariable Integer flightId,
                      @PathVariable Integer seatCount);

}
