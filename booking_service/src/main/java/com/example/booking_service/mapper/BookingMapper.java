package com.example.booking_service.mapper;

import com.example.booking_service.dto.BookingRequestDto;
import com.example.booking_service.dto.BookingResponseDto;
import com.example.booking_service.dto.BookingSearchResponse;
import com.example.booking_service.model.Booking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    private final ModelMapper modelMapper;

    public BookingMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public BookingResponseDto mapToBooking(Booking booking){
       return modelMapper.map(booking,BookingResponseDto.class);
    }
    public Booking mapToEntity(BookingRequestDto bookingRequestDto) {
        if (bookingRequestDto == null) return null;
        Booking booking = new Booking();
        booking.setPassengerId(bookingRequestDto.getPassengerId());
        booking.setFlightId(bookingRequestDto.getFlightId());
        booking.setSeatCount(bookingRequestDto.getSeatCount());
        return booking;
    }

}
