package com.example.booking_service.controller;

import com.example.booking_service.dto.BookingRequestDto;
import com.example.booking_service.dto.BookingResponseDto;
import com.example.booking_service.dto.BookingSearchRequest;
import com.example.booking_service.dto.BookingSearchResponse;
import com.example.booking_service.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }


    @PostMapping
    public ResponseEntity<BookingResponseDto> addBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto){
        BookingResponseDto bookingResponseDto = bookingService.addBooking(bookingRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponseDto);

    }

    @PutMapping("/{bookingReference}")
    public ResponseEntity<BookingResponseDto> updateBooking(@PathVariable("bookingReference")String bookingReference,
                                                            @Valid @RequestBody BookingRequestDto bookingRequestDto){
        BookingResponseDto bookingResponseDto = bookingService.updateBooking(bookingRequestDto,bookingReference);
        return ResponseEntity.ok(bookingResponseDto);
    }

    @PutMapping("/cancel/{bookingReference}")
    public ResponseEntity<String> cancelBooking(@PathVariable("bookingReference") String bookingReference){
        bookingService.cancelBooking(bookingReference);
        return ResponseEntity.ok("The booking has been cancelled successfully");
    }

    @GetMapping("/reference/{bookingReference}")
    public ResponseEntity<BookingResponseDto>retrieveBooking(@PathVariable String bookingReference){
        BookingResponseDto bookingResponseDto = bookingService.retrieveBooking(bookingReference);
        return ResponseEntity.ok(bookingResponseDto);
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity <List<BookingResponseDto>>retrieveAllBookingsByPassenger(@PathVariable("passengerId")Integer passengerId){
        List<BookingResponseDto> bookingResponseDto =bookingService.retrieveAllBookingsByPassenger(passengerId);
        return ResponseEntity.ok(bookingResponseDto);
    }

    @PostMapping("/search")
    public ResponseEntity<BookingSearchResponse> searchBooking(@Valid @RequestBody BookingSearchRequest bookingSearchRequest){
        BookingSearchResponse bookingSearchResponse = bookingService.searchBooking(bookingSearchRequest);
        return ResponseEntity.ok(bookingSearchResponse);
    }

}
