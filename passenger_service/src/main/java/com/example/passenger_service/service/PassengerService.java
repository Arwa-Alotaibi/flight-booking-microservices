package com.example.passenger_service.service;

import com.example.passenger_service.dto.PassengerRequestDto;
import com.example.passenger_service.dto.PassengerResponseDto;

import java.util.List;

public interface PassengerService {

     PassengerResponseDto addPassenger(PassengerRequestDto PassengerRequestDto);
     List<PassengerResponseDto> getAllPassenger();
    PassengerResponseDto getPassengerById(Integer PassengerId);
    PassengerResponseDto updatePassenger(Integer PassengerId, PassengerRequestDto updatePassenger);
     void deletePassenger(Integer PassengerId);
}
