package com.example.flight_service.controller;

import com.example.flight_service.dto.FlightRequestDto;
import com.example.flight_service.dto.FlightResponseDto;
import com.example.flight_service.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/flights")
public class FlightController {

    @Autowired FlightService flightService;

    @PostMapping("/add")
    public ResponseEntity<FlightResponseDto> addFlight(@Valid @RequestBody FlightRequestDto flightRequestDto){
        FlightResponseDto flightResponseDto = flightService.addFlight(flightRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(flightResponseDto);
    }
    @GetMapping("/flight/{flightNumber}")
    public ResponseEntity<FlightResponseDto> getFlightByFlightNumber(@PathVariable String flightNumber ){
        FlightResponseDto flightResponseDto = flightService.getFlightByFlightNumber(flightNumber);
        return ResponseEntity.ok(flightResponseDto);
    }
    @GetMapping("/all")
    public ResponseEntity <List<FlightResponseDto>> getAllFlights(){
        List<FlightResponseDto> flightResponseDtoList =flightService.getAllFlights();
        return ResponseEntity.ok(flightResponseDtoList);
    }

    @PutMapping("/update/seats/{flightId}/{seatCount}")
    public ResponseEntity<Void> updateAvailableSeats( @PathVariable Integer flightId,@PathVariable Integer seatCount){
        flightService.updateAvailableSeats(flightId,seatCount);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/release/seats/{flightId}/{seatCount}")
    public ResponseEntity <Void> releaseSeats( @PathVariable Integer flightId,@PathVariable Integer seatCount){
        flightService.releaseSeats(flightId,seatCount);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/availableSeat")
    public ResponseEntity<List<FlightResponseDto>> getFlightsWithAvailableSeats(){
        List<FlightResponseDto> flightResponseDto = flightService.getFlightsWithAvailableSeats();
        return ResponseEntity.ok(flightResponseDto);
    }

    @PutMapping("/delete/{flightId}")
    public ResponseEntity cancelFlight(@PathVariable Integer flightId){
        flightService.deleteFlight(flightId);
        return ResponseEntity.status(200).body("The flight has been cancelled successfully");
    }
}
