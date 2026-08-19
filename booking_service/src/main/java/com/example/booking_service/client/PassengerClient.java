package com.example.booking_service.client;

import com.example.booking_service.dto.PassengerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "passenger-service")
public interface PassengerClient {

    @GetMapping("/api/v1/passengers/{passengerId}")
    PassengerResponseDto getPassengerById(@PathVariable("passengerId") Integer passengerId);

}
