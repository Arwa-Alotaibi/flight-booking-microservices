package com.example.passenger_service.exception;

public class PassengerIdNotFoundException extends RuntimeException{
    public PassengerIdNotFoundException(Integer passengerId){
        super("passenger id not found:"+ passengerId);
    }
}
