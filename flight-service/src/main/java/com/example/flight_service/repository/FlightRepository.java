package com.example.flight_service.repository;

import com.example.flight_service.enums.FlightStatus;
import com.example.flight_service.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer> {
    List<Flight> findByStatus(FlightStatus status);

    Optional<Flight>findByFlightNumber(String flightNumber);

    List<Flight> findByDepartureAirportAirportCodeAndArrivalAirportAirportCode(String departureCode , String arrivalCode);

    List<Flight> findByDepartureAirportAirportCode(String departureCode);

    List<Flight> findByArrivalAirportAirportCode(String arrivalCode);

    boolean existsByFlightNumber(String flightNumber);

    List<Flight> findByAvailableSeatsGreaterThanAndStatusNot(int seat,FlightStatus flightStatus);

    List<Flight> findByStatusNot(FlightStatus flightStatus);


}
