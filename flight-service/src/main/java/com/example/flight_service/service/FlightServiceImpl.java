package com.example.flight_service.service;

import com.example.flight_service.dto.FlightRequestDto;
import com.example.flight_service.dto.FlightResponseDto;
import com.example.flight_service.dto.FlightSearchRequest;
import com.example.flight_service.dto.FlightUpdateDto;
import com.example.flight_service.enums.FlightStatus;
import com.example.flight_service.exception.ResourceIdNotFoundException;
import com.example.flight_service.exception.HandleArgumentException;
import com.example.flight_service.models.Airport;
import com.example.flight_service.models.Flight;
import com.example.flight_service.repository.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService{
     private  final FlightRepository flightRepository;
     private  final ModelMapper modelMapper;
     private  final AirportService airportService;

    public FlightServiceImpl(FlightRepository flightRepository , ModelMapper modelMapper ,  AirportService airportService){
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
        this.airportService = airportService;
    }

    @Override
    @Transactional
    public FlightResponseDto addFlight(FlightRequestDto flightRequestDto){
        validateFlightRequest(flightRequestDto);
        Airport departureAirport = airportService.getAirport(flightRequestDto.getDepartureAirportId());
        Airport arrivalAirport = airportService.getAirport(flightRequestDto.getArrivalAirportId());
        Flight flight = modelMapper.map(flightRequestDto,Flight.class);
        flight.setAvailableSeats(flightRequestDto.getTotalSeats());
        flight.setStatus(FlightStatus.SCHEDULED);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        Flight flightSaved = flightRepository.save(flight);
        return mapToFlightResponse(flightSaved);
    }

    @Override
    @Transactional
    public FlightResponseDto updateFlight(Integer flightId, FlightUpdateDto flightUpdateDto) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(()-> new ResourceIdNotFoundException("flight id not exists !"));
        if(flight.getStatus() == FlightStatus.CANCELLED){
            throw new HandleArgumentException("the flight is already deleted");
        }
        if(flightUpdateDto.getArrivalTime().isBefore(flightUpdateDto.getDepartureTime())){
            throw new HandleArgumentException("the arrival time should not before departure time");
        }
        int seatDifference = flightUpdateDto.getTotalSeats() - flight.getTotalSeats();
        int updatedAvailableSeat = flight.getAvailableSeats() + seatDifference;
        if(updatedAvailableSeat < 0 ){
            throw new HandleArgumentException(
                    "Total seats cannot be less than the number of reserved seats");
        }
        flight.setDepartureTime(flightUpdateDto.getDepartureTime());
        flight.setArrivalTime(flightUpdateDto.getArrivalTime());
        flight.setPrice(flightUpdateDto.getPrice());
        flight.setTotalSeats(flightUpdateDto.getTotalSeats());
        flight.setAvailableSeats(updatedAvailableSeat);
        return mapToFlightResponse(flight);
    }

    @Override
    @Transactional
    public void deleteFlight(Integer flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceIdNotFoundException("flight id not found"));
        if(flight.getStatus().equals(FlightStatus.CANCELLED)){
            throw new HandleArgumentException("the flight is already deleted !");
        }
        flight.setStatus(FlightStatus.CANCELLED);
    }

    public void validateFlightRequest(FlightRequestDto flightRequestDto){
        if(flightRequestDto.getArrivalAirportId().equals(flightRequestDto.getDepartureAirportId())){
            throw new HandleArgumentException("the arrivalAirport and departureAirport cannot be same ");
        }
        if(flightRepository.existsByFlightNumber(flightRequestDto.getFlightNumber())){
            throw new HandleArgumentException("the flight number is already exists");
        }
        if(flightRequestDto.getArrivalTime().isBefore(flightRequestDto.getDepartureTime())){
            throw new HandleArgumentException("the arrival time should not before departure time");
        }
    }
    @Override
    @Transactional(readOnly = true)
    public FlightResponseDto getFlightByFlightNumber(String flightNumber) {
       Flight flight = flightRepository.findByFlightNumber(flightNumber).orElseThrow(() ->
               new HandleArgumentException("the flight number is not exists"));
        return  mapToFlightResponse(flight);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightResponseDto> getAllFlights() {
        List<Flight> flightList = flightRepository.findByStatusNot(FlightStatus.CANCELLED);
        return flightList.stream()
                .map(this::mapToFlightResponse)
                .toList();
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

    @Override
    @Transactional(readOnly = true)
    public List<FlightResponseDto> getFlightsWithAvailableSeats() {
       return flightRepository.findByAvailableSeatsGreaterThanAndStatusNot(0,FlightStatus.CANCELLED).stream()
                .map(this::mapToFlightResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightResponseDto> searchFlight(FlightSearchRequest flightSearchRequest) {
        return List.of();
    }

    @Override
    @Transactional
    public void updateAvailableSeats(Integer flightId,Integer seatCount){
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new ResourceIdNotFoundException("flight id not found"));
        if( flight.getAvailableSeats() == null || flight.getAvailableSeats() < seatCount){
            throw new HandleArgumentException("Not enough available seats on this flight");
        }
        flight.setAvailableSeats(flight.getAvailableSeats() - seatCount);
    }

    @Override
    @Transactional
    public void releaseSeats(Integer flightId, Integer seatsCount) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceIdNotFoundException("flight id not found"));
        int availableSeat = flight.getAvailableSeats() + seatsCount;
        if(availableSeat > flight.getTotalSeats()){
            throw new HandleArgumentException("the available seats cannot be greater than total seats");
        }
        flight.setAvailableSeats(availableSeat);
    }
}
