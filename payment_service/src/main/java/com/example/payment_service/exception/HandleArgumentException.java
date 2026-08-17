package com.example.payment_service.exception;

public class HandleArgumentException extends  IllegalArgumentException{
    public HandleArgumentException(String message){
        super(message);
    }
}
