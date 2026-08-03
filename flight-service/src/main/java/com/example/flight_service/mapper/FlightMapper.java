package com.example.flight_service.mapper;

import com.example.flight_service.dto.FlightResponseDto;
import com.example.flight_service.models.Flight;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {
    private  final ModelMapper modelMapper;

    public FlightMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    public FlightResponseDto mapToFlightResponse(Flight flight){
        FlightResponseDto flightResponseDto = modelMapper.map(flight,FlightResponseDto.class);
        if(flight.getDepartureAirport()!=null){
            flightResponseDto.setDepartureAirportCode(flight.getDepartureAirport().getAirportCode());
        }
        if(flight.getArrivalAirport()!=null){
            flightResponseDto.setArrivalAirportCode(flight.getArrivalAirport().getAirportCode());
        }
        return flightResponseDto;
    }

}
