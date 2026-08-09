package com.example.passenger_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PassengerRequestDto {
    @NotBlank(message = "the first name is required")
    private String firstName;
    @NotBlank(message = "the last name is required")
    private String lastName;
    @NotBlank(message = "the email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    @NotBlank(message = "the phone Number is required")
    private String phoneNumber;
    @NotBlank(message = "the identity Number is required")
    private String identityNumber;

}
