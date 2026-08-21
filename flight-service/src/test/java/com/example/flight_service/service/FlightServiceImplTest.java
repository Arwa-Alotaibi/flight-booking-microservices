package com.example.flight_service.service;

import com.example.flight_service.dto.FlightRequestDto;
import com.example.flight_service.dto.FlightResponseDto;
import com.example.flight_service.enums.FlightStatus;
import com.example.flight_service.exception.HandleArgumentException;
import com.example.flight_service.exception.ResourceIdNotFoundException;
import com.example.flight_service.mapper.FlightMapper;
import com.example.flight_service.models.Airport;
import com.example.flight_service.models.Flight;
import com.example.flight_service.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightServiceImplTest {
    @Mock
    private FlightRepository flightRepository;


    @InjectMocks
    private FlightServiceImpl flightService;

    @Mock
    private FlightMapper flightMapper;

    @Mock
    private AirportService airportService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void shouldReturnFlightWhenFlightNumberExists(){
        Flight flight = new Flight();
        flight.setFlightNumber("SV-290");
        when(flightRepository.findByFlightNumber("SV-290"))
                .thenReturn(Optional.of(flight));
        FlightResponseDto response = new FlightResponseDto();
        response.setFlightNumber("SV-290");

        when(flightMapper.mapToFlightResponse(flight))
                .thenReturn(response);
         response =
                flightService.getFlightByFlightNumber("SV-290");

        assertEquals("SV-290",response.getFlightNumber());
    }

    @Test
    public void shouldThrowExceptionWhenFlightNumberDoesNotExist(){
        when(flightRepository.findByFlightNumber("SV-290"))
                .thenReturn(Optional.empty());

        ResourceIdNotFoundException exception =
                assertThrows(
                        ResourceIdNotFoundException.class,
                        ()->flightService.getFlightByFlightNumber("SV-290"));
        assertEquals("the flight number is not exists", exception.getMessage());
    }

    @Test
    public void shouldReturnFlightWhenFlightIdExists(){
        Flight flight = new Flight();
        flight.setFlightId(1);
        FlightResponseDto response = new FlightResponseDto();
        response.setFlightNumber("SV-290");


        when(flightRepository.findById(1))
                .thenReturn(Optional.of(flight));
        when(flightMapper.mapToFlightResponse(flight))
                .thenReturn(response);

        response = flightService.getFlightByFlightId(1);

        assertEquals("SV-290",response.getFlightNumber());

    }

    @Test
    public void shouldThrowExceptionWhenFlightIdDoesNotExist(){
        when(flightRepository.findById(1))
                .thenReturn(Optional.empty());
        ResourceIdNotFoundException exception = assertThrows(
                ResourceIdNotFoundException.class,
                ()-> flightService.getFlightByFlightId(1));

        assertEquals("the flight id is not exists", exception.getMessage());
    }

  public Flight buildFlight(){
        Flight flight =
                Flight.builder()
                        .flightId(1)
                        .flightNumber("SV-22")
                        .price(BigDecimal.valueOf(55.5))
                        .availableSeats(11)
                        .arrivalTime(LocalDateTime.parse("2026-08-21T08:00:00"))
                        .departureTime(LocalDateTime.parse("2026-08-21T08:00:00"))
                        .totalSeats(44)
                        .status(FlightStatus.SCHEDULED)
                        .build();

        return flight;
  }

    public FlightRequestDto buildFlightRequest(){
       return FlightRequestDto.builder()
                        .flightNumber("SV-22")
                        .price(BigDecimal.valueOf(55.5))
                        .departureAirportId(1)
                        .arrivalAirportId(2)
                        .arrivalTime(LocalDateTime.parse("2026-08-21T08:00:00"))
                        .departureTime(LocalDateTime.parse("2026-08-21T08:00:00"))
                        .totalSeats(44)
                        .build();

    }

    public FlightResponseDto buildFlightResponse(){
        return  FlightResponseDto.builder()
                .flightNumber("SV-22")
                .price(BigDecimal.valueOf(55.5))
                .availableSeats(11)
                .totalSeats(44)
                .status(FlightStatus.SCHEDULED)
                .build();
    }


   @Test
    public void shouldCreateFlightSuccessfully(){
        FlightRequestDto flightRequestDto = buildFlightRequest();
        Flight flight = buildFlight();
        FlightResponseDto flightResponseDto = buildFlightResponse();
       Airport departureAirport = new Airport();
       Airport arrivalAirport = new Airport();

       when(airportService.getAirport(1)).thenReturn(departureAirport);
       when(airportService.getAirport(2)).thenReturn(arrivalAirport);
       when(modelMapper.map(flightRequestDto,Flight.class)).thenReturn(flight);
       when(flightRepository.save(any(Flight.class))).thenReturn(flight);
       when(flightMapper.mapToFlightResponse(flight)).thenReturn(flightResponseDto);
       flightResponseDto = flightService.addFlight(flightRequestDto);
       assertNotNull(flightResponseDto);
       assertEquals("SV-22", flightResponseDto.getFlightNumber());
   }

   @Test
    public void shouldFailWhenFlightNumberAlreadyExists(){
       FlightRequestDto flightRequestDto = buildFlightRequest();
       when(flightRepository.existsByFlightNumber(flightRequestDto.getFlightNumber()))
               .thenReturn(true);

       HandleArgumentException exception = assertThrows(
               HandleArgumentException.class,
             ()-> flightService.validateFlightRequest(flightRequestDto));

       assertEquals("the flight number is already exists", exception.getMessage());
   }
   @Test
    public void shouldFailWhenAirportsAreSame(){
        FlightRequestDto flight = new FlightRequestDto();
        flight.setArrivalAirportId(1);
        flight.setDepartureAirportId(1);

        HandleArgumentException exception =
                assertThrows(
                        HandleArgumentException.class,
                        ()->flightService.validateFlightRequest(flight));

       assertEquals("the arrivalAirport and departureAirport cannot be same ", exception.getMessage());

   }

   @Test
    public void  shouldFailWhenArrivalTimeIsBeforeDepartureTime(){
       FlightRequestDto flight = new FlightRequestDto();
       flight.setArrivalAirportId(1);
       flight.setDepartureAirportId(2);
       flight.setArrivalTime(LocalDateTime.parse("2026-08-21T07:00:00"));
       flight.setDepartureTime(LocalDateTime.parse("2026-08-21T08:00:00"));

       HandleArgumentException exception =  assertThrows(
               HandleArgumentException.class,
               ()->flightService.validateFlightRequest(flight));
       assertEquals("the arrival time should not before departure time", exception.getMessage());
   }

   @Test
    public void shouldCancelFlightSuccessfully(){
        Flight flight = buildFlight();

       when(flightRepository.findById(flight.getFlightId()))
               .thenReturn(Optional.of(flight));

        flightService.deleteFlight(flight.getFlightId());
       assertEquals(FlightStatus.CANCELLED, flight.getStatus());
   }

   @Test
    void shouldFailWhenFlightIsAlreadyCancelled(){
        Flight flight = new Flight();
        flight.setFlightId(1);
        flight.setStatus(FlightStatus.CANCELLED);
       when(flightRepository.findById(flight.getFlightId()))
               .thenReturn(Optional.of(flight));
       HandleArgumentException exception = assertThrows(
                HandleArgumentException.class,
                ()->flightService.deleteFlight(flight.getFlightId())
        );
       assertEquals("the flight is already deleted !", exception.getMessage());
   }

}
