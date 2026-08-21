package com.example.Booking_service.service;

import com.example.booking_service.client.PaymentClient;
import com.example.booking_service.dto.BookingRequestDto;
import com.example.booking_service.dto.BookingResponseDto;
import com.example.booking_service.dto.PaymentResponseDto;
import com.example.booking_service.enums.BookingStatus;
import com.example.booking_service.enums.PaymentMethod;
import com.example.booking_service.enums.PaymentStatus;
import com.example.booking_service.exception.HandleArgumentException;
import com.example.booking_service.exception.ResourceNotFoundException;
import com.example.booking_service.mapper.BookingMapper;
import com.example.booking_service.model.Booking;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.service.BookingServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {
    @Mock BookingRepository bookingRepository;
    @InjectMocks private BookingServiceImpl bookingService;
    @Mock BookingMapper bookingMapper;
    @Mock PaymentClient paymentClient;




   @Test
   void confirmBookingPaymentSuccessfully(){
       Booking booking = buildBooking();
       PaymentResponseDto paymentResponseDto = buildPaymentResponse();
       BookingResponseDto bookingResponseDto = new BookingResponseDto();

       when(bookingRepository.findByBookingReference(booking.getBookingReference()))
               .thenReturn(Optional.of(booking));
       when(paymentClient.processPayment(booking.getBookingId()))
               .thenReturn(paymentResponseDto);
       when(bookingMapper.mapToBooking(booking))
               .thenReturn(bookingResponseDto);

       BookingResponseDto result = bookingService.
               confirmBookingPayment(booking.getBookingReference());

       assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus());
       assertNotNull(result);
       verify(paymentClient).processPayment(booking.getBookingId());
       verify(bookingMapper).mapToBooking(booking);

   }

   @Test
   void shouldFailWhenBookingReferenceDoesNotExist(){
       String bookingReference = "BK-123";

       when(bookingRepository.findByBookingReference(bookingReference))
               .thenReturn(Optional.empty());
       ResourceNotFoundException exception = assertThrows(
               ResourceNotFoundException.class,
               ()-> bookingService.confirmBookingPayment(bookingReference)
       );
       assertEquals("Booking reference does not exist.",exception.getMessage());
       verify(paymentClient, never())
               .processPayment(any());
   }

   @Test
   void shouldFailWhenBookingIsCancelled(){
       Booking booking = buildBooking();
       booking.setBookingStatus(BookingStatus.CANCELLED);
       when(bookingRepository.findByBookingReference(booking.getBookingReference()))
               .thenReturn(Optional.of(booking));

       HandleArgumentException exception = assertThrows(
               HandleArgumentException.class,
               ()-> bookingService.confirmBookingPayment(booking.getBookingReference())
       );
       assertEquals("Cannot confirm payment for a cancelled booking.",exception.getMessage());
       verify(paymentClient, never())
               .processPayment(any());

   }

   @Test
   void shouldFailWhenBookingPaymentIsAlreadyConfirmed(){
       Booking booking = buildBooking();
       booking.setBookingStatus(BookingStatus.COMPLETED);

       when(bookingRepository.findByBookingReference(booking.getBookingReference()))
               .thenReturn(Optional.of(booking));

       HandleArgumentException exception = assertThrows(
               HandleArgumentException.class,
               ()-> bookingService.confirmBookingPayment(booking.getBookingReference())
       );
       assertEquals("Booking payment is already confirmed.",exception.getMessage());
       verify(paymentClient, never())
               .processPayment(any());

   }
   @Test
   void retrieveBookingSuccessfully(){
       Booking booking = buildBooking();
       BookingResponseDto bookingResponseDto = buildBookingRes();
       when(bookingRepository.findByBookingReference(booking.getBookingReference()))
               .thenReturn(Optional.of(booking));

       when(bookingMapper.mapToBooking(booking))
               .thenReturn(bookingResponseDto);

       BookingResponseDto result =
               bookingService.retrieveBooking(booking.getBookingReference());

       assertNotNull(result);
       assertEquals(booking.getBookingReference(),
                    result.getBookingReference());
       verify(bookingRepository)
               .findByBookingReference(booking.getBookingReference());
       verify(bookingMapper)
               .mapToBooking(booking);
   }

    private BookingRequestDto buildBookingRequest(){
        return BookingRequestDto.builder()
                .passengerId(1)
                .flightId(2)
                .seatCount(33)
                .paymentMethod(PaymentMethod.MADA)
                .build();
    }
    private  BookingResponseDto buildBookingRes(){
        return BookingResponseDto.builder()
                .bookingReference("BK-11")
                .totalPrice(BigDecimal.TEN)
                .seatCount(33)
                .bookingDate(LocalDateTime.parse("2026-08-21T08:00:00"))
                .bookingStatus(BookingStatus.PENDING)
                .page(1)
                .size(2)
                .build();
    }
   private Booking buildBooking(){
        return Booking.builder()
                .bookingId(1)
                .bookingReference("BK-11")
                .passengerId(1)
                .flightId(2)
                .totalPrice(BigDecimal.TEN)
                .seatCount(33)
                .bookingDate(LocalDateTime.parse("2026-08-21T08:00:00"))
                .bookingStatus(BookingStatus.PENDING)
                .build();
    }

    public PaymentResponseDto buildPaymentResponse() {
        return PaymentResponseDto.builder()
                .paymentId(1L)
                .bookingId(10)
                .amount(new BigDecimal("460.50"))
                .paymentMethod(PaymentMethod.MADA)
                .paymentStatus(PaymentStatus.PAID)
                .build();
    }


}
