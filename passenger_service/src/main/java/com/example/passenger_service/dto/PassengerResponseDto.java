package com.example.passenger_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassengerResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;

}
