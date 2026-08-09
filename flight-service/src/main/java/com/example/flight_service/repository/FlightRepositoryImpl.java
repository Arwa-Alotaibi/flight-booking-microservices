package com.example.flight_service.repository;

import com.example.flight_service.dto.FlightSearchRequest;
import com.example.flight_service.enums.FlightStatus;
import com.example.flight_service.mapper.FlightMapper;
import com.example.flight_service.models.Flight;
import com.example.flight_service.models.QFlight;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class FlightRepositoryImpl implements FlightRepositoryCustom{
    private JPAQueryFactory queryFactory;
    private final FlightMapper flightMapper;


    public FlightRepositoryImpl(JPAQueryFactory queryFactory, FlightMapper flightMapper){
        this.queryFactory = queryFactory;
        this.flightMapper = flightMapper;
    }

    @Override
    public List<Flight> searchFlights(FlightSearchRequest flightSearchRequest) {
        QFlight flight = QFlight.flight;
        BooleanBuilder builder = new BooleanBuilder();
        if(flightSearchRequest.getStatus()==null){
            builder.and(flight.status.ne(FlightStatus.CANCELLED));
        }
     else{
            builder.and(flight.status.eq(flightSearchRequest.getStatus()));
        }
        if(flightSearchRequest.getMaxPrice()!=null){
            builder.and(flight.price.loe(flightSearchRequest.getMaxPrice()));
        }
        if(flightSearchRequest.getDepartureAirportCode()!=null){
            builder.and(flight.departureAirport.airportCode.equalsIgnoreCase(flightSearchRequest.getDepartureAirportCode()));
        }
        if(flightSearchRequest.getArrivalAirportCode()!=null){
            builder.and(flight.arrivalAirport.airportCode.equalsIgnoreCase(flightSearchRequest.getArrivalAirportCode()));
        }
        if(flightSearchRequest.getDepartureDate()!=null){
            LocalDateTime start =
                    flightSearchRequest.getDepartureDate().atStartOfDay();

            LocalDateTime end =
                    flightSearchRequest.getDepartureDate().atTime(LocalTime.MAX);

            builder.and(flight.departureTime.between(start,end));
        }
        int page = flightSearchRequest.getPage() !=null ? flightSearchRequest.getPage() : 0;
        int size = flightSearchRequest.getSize() !=null ? flightSearchRequest.getSize() : 10;
       return  queryFactory.selectFrom(flight)
                .leftJoin(flight.arrivalAirport).fetchJoin()
                .leftJoin(flight.departureAirport).fetchJoin()
                .where(builder)
               .offset((long) page * size)
               .limit(size)
               .orderBy(flight.price.asc())
                .fetch();
    }
    @Override
    public List<Flight> getCheapestFlight(){
        QFlight flight = QFlight.flight;
        return queryFactory.selectFrom(flight)
                .where(flight.status.ne(FlightStatus.CANCELLED))
                .orderBy(flight.price.asc())
                .limit(5)
                .fetch();
    }
}
