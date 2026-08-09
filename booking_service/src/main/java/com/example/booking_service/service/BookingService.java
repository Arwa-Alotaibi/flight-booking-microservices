package com.example.booking_service.service;

import com.example.booking_service.dto.BookingRequestDto;
import com.example.booking_service.dto.BookingResponseDto;
import com.example.booking_service.dto.BookingSearchRequest;
import com.example.booking_service.dto.BookingSearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    BookingResponseDto addBooking(BookingRequestDto bookingRequestDto);
    BookingResponseDto updateBooking(BookingRequestDto bookingRequestDto,String bookingReference);
    void cancelBooking(String bookingReference);
    BookingResponseDto retrieveBooking(String bookingReference);
    List<BookingResponseDto> retrieveAllBookingsByPassenger(Integer passengerId);
    BookingSearchResponse searchBooking(BookingSearchRequest bookingSearchRequest);
    BookingResponseDto confirmBookingPayment(String bookingReference);



}
