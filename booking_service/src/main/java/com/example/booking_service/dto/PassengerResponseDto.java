package com.example.booking_service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassengerResponseDto {
    private String firstName;
    private String email;
    private String phoneNumber;
}