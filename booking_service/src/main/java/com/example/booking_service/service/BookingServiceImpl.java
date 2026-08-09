package com.example.booking_service.service;
import com.example.booking_service.dto.*;
import com.example.booking_service.enums.BookingStatus;
import com.example.booking_service.enums.PaymentStatus;
import com.example.booking_service.exception.HandleArgumentException;
import com.example.booking_service.exception.ResourceNotFoundException;
import com.example.booking_service.mapper.BookingMapper;
import com.example.booking_service.model.Booking;
import com.example.booking_service.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final BookingValidationService bookingValidationService;
    private final BookingMapper bookingMapper;


    public BookingServiceImpl(BookingRepository bookingRepository,
                              BookingValidationService bookingValidationService,
                              BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingValidationService = bookingValidationService;
        this.bookingMapper = bookingMapper;
    }
    @Override
    @Transactional
    public BookingResponseDto addBooking(BookingRequestDto bookingRequestDto) {
        bookingValidationService.validatePassengerId(bookingRequestDto.getPassengerId());
        bookingValidationService.validateNoPendingBooking(bookingRequestDto.getPassengerId());
        FlightResponseDto flightResponseDto = bookingValidationService.validateFlightId(bookingRequestDto.getFlightId());
        BigDecimal price = bookingValidationService.calculateBookingPrice(flightResponseDto.getPrice(),bookingRequestDto.getSeatCount());
        bookingValidationService.updateAvailableSeats(bookingRequestDto.getFlightId(),bookingRequestDto.getSeatCount());
        Booking booking = bookingMapper.mapToEntity(bookingRequestDto);
        booking.setBookingReference("BK-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        booking.setTotalPrice(price);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setPaymentStatus(PaymentStatus.UNPAID);

        Booking bookingSave = bookingRepository.save(booking);

        return bookingMapper.mapToBooking(bookingSave);
    }


    @Override
    @Transactional
    public BookingResponseDto updateBooking(BookingRequestDto bookingRequestDto,String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference).orElseThrow(()->
                new ResourceNotFoundException("Booking reference does not exist."));
        bookingValidationService.validateUpdateBooking(bookingRequestDto,booking);
        boolean isFlightChanged = !Objects.equals(booking.getFlightId(),bookingRequestDto.getFlightId());
        boolean isSeatCountChanged = !Objects.equals(booking.getSeatCount(), bookingRequestDto.getSeatCount());

        if(isFlightChanged || isSeatCountChanged ){
            FlightResponseDto flightResponseDto = bookingValidationService.validateFlightId(bookingRequestDto.getFlightId());
            bookingValidationService.updateReleaseSeats(booking.getFlightId() , booking.getSeatCount());
            bookingValidationService.updateAvailableSeats(bookingRequestDto.getFlightId(), bookingRequestDto.getSeatCount());
            BigDecimal price = bookingValidationService.calculateBookingPrice(flightResponseDto.getPrice(),bookingRequestDto.getSeatCount());
            booking.setTotalPrice(price);
            if(isFlightChanged){
                booking.setFlightId(bookingRequestDto.getFlightId());
            }
            if(isSeatCountChanged){
                booking.setSeatCount(bookingRequestDto.getSeatCount());
            }
        }
        return bookingMapper.mapToBooking(booking);
    }

    @Override
    @Transactional
    public void cancelBooking(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference).orElseThrow(
                ()-> new ResourceNotFoundException("Booking reference does not exist."));

        if(booking.getBookingStatus().equals(BookingStatus.CANCELLED)){
            throw new HandleArgumentException("Booking is already cancelled.");
        }
        bookingValidationService.updateReleaseSeats(booking.getFlightId() , booking.getSeatCount());
        booking.setBookingStatus(BookingStatus.CANCELLED);
    }


    @Override
    public BookingResponseDto retrieveBooking(String bookingReference) {
      Booking booking = bookingRepository.findByBookingReference(bookingReference)
              .orElseThrow(()-> new ResourceNotFoundException("Booking reference does not exist."));
        return bookingMapper.mapToBooking(booking);
    }

    @Override
    public List<BookingResponseDto> retrieveAllBookingsByPassenger(Integer passengerId) {
        bookingValidationService.validatePassengerId(passengerId);
        List<Booking> bookingList = bookingRepository.findByPassengerId(passengerId);
         return bookingList.stream()
                .map(bookingMapper::mapToBooking)
                .toList();
    }

    @Override
    public BookingSearchResponse searchBooking(BookingSearchRequest bookingSearchRequest) {
        List<Booking> bookingSearch = bookingRepository.searchBooking(bookingSearchRequest);
        List<BookingResponseDto> bookingList = bookingSearch.stream()
                .map(bookingMapper::mapToBooking)
                .toList();
        Long totalBookings = bookingRepository.searchCount(bookingSearchRequest);
       return BookingSearchResponse.builder()
                .countSearch(totalBookings)
                .bookingResponseDtoList(bookingList)
                .build();
    }

    @Override
    @Transactional
    public BookingResponseDto confirmBookingPayment(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(()-> new ResourceNotFoundException("Booking reference does not exist."));

        if(booking.getBookingStatus().equals(BookingStatus.CANCELLED)){
            throw new HandleArgumentException("Cannot confirm payment for a cancelled booking.");
        }
        if(booking.getPaymentStatus().equals(PaymentStatus.PAID)){
            throw new HandleArgumentException("Booking payment has already been confirmed.");
        }
        booking.setBookingStatus(BookingStatus.COMPLETED);
        booking.setPaymentStatus(PaymentStatus.PAID);
        return bookingMapper.mapToBooking(booking);
    }
}
