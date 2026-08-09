package com.example.passenger_service.repository;

import com.example.passenger_service.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    boolean existsByIdentityNumber(String identityNumber);
}
