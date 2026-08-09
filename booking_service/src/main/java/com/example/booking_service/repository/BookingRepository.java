package com.example.booking_service.repository;

import com.example.booking_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> ,BookingRepositoryCustom{
    List<Booking> findByPassengerId(Integer passengerId);
    Optional<Booking> findByBookingReference(String bookingReference);
}
