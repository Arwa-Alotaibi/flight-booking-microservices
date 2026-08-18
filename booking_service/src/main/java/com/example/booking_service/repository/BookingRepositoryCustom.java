package com.example.booking_service.repository;

import com.example.booking_service.dto.BookingSearchRequest;
import com.example.booking_service.enums.BookingStatus;
import com.example.booking_service.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingRepositoryCustom {

    List<Booking> searchBooking(BookingSearchRequest bookingSearchRequest);
    Long searchCount(BookingSearchRequest bookingSearchRequest);
    Long countBookingsByStatus(BookingStatus bookingStatus);
    boolean hasPendingBooking(Integer passengerId);

}
