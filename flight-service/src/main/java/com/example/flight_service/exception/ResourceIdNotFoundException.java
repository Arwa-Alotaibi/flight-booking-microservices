package com.example.flight_service.exception;

public class ResourceIdNotFoundException extends RuntimeException{
    public ResourceIdNotFoundException(String message){
        super(message);
    }
}
