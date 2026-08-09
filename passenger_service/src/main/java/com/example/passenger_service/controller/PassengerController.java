package com.example.passenger_service.controller;

import com.example.passenger_service.dto.PassengerRequestDto;
import com.example.passenger_service.dto.PassengerResponseDto;
import com.example.passenger_service.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<PassengerResponseDto> addPassenger(@Valid @RequestBody PassengerRequestDto passengerRequestDto){
        PassengerResponseDto passengerRequestDtoAdded = passengerService.addPassenger(passengerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerRequestDtoAdded);
    }

    @GetMapping
    public ResponseEntity<List<PassengerResponseDto>> getAllPassenger(){
        List<PassengerResponseDto> allPassenger = passengerService.getAllPassenger();
        return ResponseEntity.ok(allPassenger);
    }

    @PutMapping("/update/{passengerId}")
    public ResponseEntity<PassengerResponseDto> updatePassenger(@PathVariable Integer passengerId , @Valid @RequestBody PassengerRequestDto passengerRequestDto){
        PassengerResponseDto passengerResponseDto = passengerService.updatePassenger(passengerId,passengerRequestDto);
        return ResponseEntity.ok(passengerResponseDto);
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<PassengerResponseDto> getPassengerById(@PathVariable Integer passengerId){
        PassengerResponseDto passengerResponseDto = passengerService.getPassengerById(passengerId);
        return ResponseEntity.ok(passengerResponseDto);
    }

    @DeleteMapping("delete/{passengerId}")
    public ResponseEntity deletePassenger(@PathVariable Integer passengerId){
        passengerService.deletePassenger(passengerId);
        return ResponseEntity.ok("Passenger deleted");

    }
}
