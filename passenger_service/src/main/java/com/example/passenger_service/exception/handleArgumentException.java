package com.example.passenger_service.exception;

public class handleArgumentException extends IllegalArgumentException {
    public handleArgumentException() {
        super("Passenger is already exists");
    }
}
