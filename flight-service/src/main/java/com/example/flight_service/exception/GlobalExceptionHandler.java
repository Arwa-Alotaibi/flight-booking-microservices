package com.example.flight_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HandleArgumentException.class)
    public ResponseEntity<Object> handleArgumentException(HandleArgumentException exception){
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ResourceIdNotFoundException.class)
    public ResponseEntity<Object>PassengerIdNotFoundException(ResourceIdNotFoundException exception){
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    public ResponseEntity<Object> buildResponse(HttpStatus status , String message){
        Map<String,Object> body= new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
