package com.example.flight_service.service;

import com.example.flight_service.exception.ResourceIdNotFoundException;
import com.example.flight_service.models.Airport;
import com.example.flight_service.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AirportServiceImpl implements AirportService{
    @Autowired
    private AirportRepository airportRepository;

    public Airport getAirport(Integer airportId){
        return airportRepository.findById(airportId)
                .orElseThrow(() -> new ResourceIdNotFoundException("airport id not fount: "+airportId));

    }

    public String getAirportCode(Airport airport){
        return Optional.ofNullable(airport)
                .map(Airport::getAirportCode)
                .orElse("not found");
    }

}
