package com.example.booking_service.service;


import com.example.booking_service.client.FlightClient;
import com.example.booking_service.client.PassengerClient;
import com.example.booking_service.dto.BookingRequestDto;
import com.example.booking_service.dto.FlightResponseDto;
import com.example.booking_service.enums.BookingStatus;
import com.example.booking_service.enums.PaymentStatus;
import com.example.booking_service.exception.HandleArgumentException;
import com.example.booking_service.exception.ResourceNotFoundException;
import com.example.booking_service.model.Booking;
import com.example.booking_service.repository.BookingRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BookingValidationService {
    private final  FlightClient flightClient;
    private final  PassengerClient passengerClient;
    private final  BookingRepository bookingRepository;

    public BookingValidationService(FlightClient flightClient,PassengerClient passengerClient ,BookingRepository bookingRepository ){
        this.flightClient = flightClient;
        this.passengerClient = passengerClient;
        this.bookingRepository = bookingRepository;
    }

    public void validatePassengerId(Integer passengerId) {
        try{
            passengerClient.getPassengerById(passengerId);
        }
        catch (FeignException.NotFound ex){
            throw new ResourceNotFoundException("Passenger not found: " + passengerId);
        }
    }

    public FlightResponseDto validateFlightId(Integer flightId){
        try{
           return flightClient.getFlightByFlightId(flightId);
        }
        catch (FeignException.NotFound ex){
            throw new ResourceNotFoundException("flightId not found: " + flightId);
        }

    }

    public void updateAvailableSeats(Integer flightId, Integer seatsCount){
        try {
            flightClient.updateAvailableSeats(flightId, seatsCount);
        }
        catch (FeignException.BadRequest ex){
            throw new HandleArgumentException("Not enough available seats on this flight.");
        }
        catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException(
                    "Flight not found: " + flightId
            );
        }
    }


    public BigDecimal calculateBookingPrice(BigDecimal price, Integer seatsCount){
         return price.multiply(BigDecimal.valueOf(seatsCount));
    }

    public void validateNoPendingBooking(Integer passengerId){
        boolean hasPendingBooking= bookingRepository.hasPendingBooking(passengerId);
        if(hasPendingBooking){
            throw new HandleArgumentException("The passenger already has a pending booking.");
        }

    }

    public void validateUpdateBooking(BookingRequestDto bookingRequestDto, Booking booking) {
        if (!booking.getBookingStatus().equals(BookingStatus.PENDING)) {
            throw new HandleArgumentException("Sorry, you can only update pending bookings.");
        }

        if (!booking.getPassengerId().equals(bookingRequestDto.getPassengerId())) {
            throw new HandleArgumentException("Passenger ID cannot be changed for an existing booking.");
        }
    }

    public void updateReleaseSeats(Integer flightId, Integer seatCount) {
        try {
            flightClient.releaseSeats(flightId, seatCount);
        } catch (FeignException.BadRequest ex) {
            throw new HandleArgumentException(
                    "Cannot release seats because it would exceed the flight capacity."
            );
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException(
                    "Flight not found: " + flightId
            );
        }
    }
}
