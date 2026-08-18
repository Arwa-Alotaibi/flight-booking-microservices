package com.example.booking_service.repository;

import com.example.booking_service.dto.BookingSearchRequest;
import com.example.booking_service.enums.BookingStatus;
import com.example.booking_service.enums.PaymentStatus;
import com.example.booking_service.model.Booking;
import com.example.booking_service.model.QBooking;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BookingRepositoryImpl implements BookingRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public BookingRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }
    public BooleanBuilder buildWhere(BookingSearchRequest bookingSearchRequest){
        QBooking booking = QBooking.booking;
        BooleanBuilder where = new BooleanBuilder();
        if(bookingSearchRequest.getPassengerId() !=null){
            where.and(booking.passengerId.eq(bookingSearchRequest.getPassengerId()));
        }
        if(bookingSearchRequest.getFlightId()!=null){
            where.and(booking.flightId.eq(bookingSearchRequest.getFlightId()));
        }
        if(bookingSearchRequest.getBookingReference()!=null){
            where.and(booking.bookingReference.eq(bookingSearchRequest.getBookingReference()));
        }
        if(bookingSearchRequest.getBookingStatus()!=null){
            where.and(booking.bookingStatus.eq(bookingSearchRequest.getBookingStatus()));
        }
        if(bookingSearchRequest.getFromDate()!=null ){
            where.and(booking.bookingDate.goe(bookingSearchRequest.getFromDate().atStartOfDay()));
        }
        if(bookingSearchRequest.getToDate()!=null ){
            where.and(booking.bookingDate.loe(bookingSearchRequest.getToDate().atTime(LocalTime.MAX)));
        }
        return  where;
    }
    @Override
    public List<Booking> searchBooking(BookingSearchRequest bookingSearchRequest){
        QBooking booking = QBooking.booking;
        BooleanBuilder where = buildWhere(bookingSearchRequest);

        int page = bookingSearchRequest.getPage()!=null && bookingSearchRequest.getPage()>=0 ?  bookingSearchRequest.getPage() :0;
        int size = bookingSearchRequest.getSize()!=null && bookingSearchRequest.getSize()> 0 ? bookingSearchRequest.getSize(): 10;

        return queryFactory.selectFrom(booking)
                .where(where)
                .offset((long) page * size)
                .limit(size)
                .orderBy(booking.bookingDate.desc())
                .fetch();

    }

    @Override
    public Long searchCount(BookingSearchRequest bookingSearchRequest){
        QBooking booking = QBooking.booking;
        BooleanBuilder where = buildWhere(bookingSearchRequest);
       return Optional.ofNullable(queryFactory.select(booking.count())
                .from(booking)
                .where(where)
                .fetchOne())
               .orElse(0L);
    }

    @Override
    public Long countBookingsByStatus(BookingStatus bookingStatus){
        QBooking booking = QBooking.booking;
        return  Optional.ofNullable(queryFactory.select(booking.count())
                 .from(booking)
                .where(booking.bookingStatus.eq(bookingStatus))
                .fetchOne()) .orElse(0L);

    }


    @Override
    public boolean hasPendingBooking(Integer passengerId) {
        QBooking booking = QBooking.booking;
        Long count = Optional.ofNullable(queryFactory.select(booking.count())
                .from(booking)
                .where(booking.passengerId.eq(passengerId).and(booking.bookingStatus.eq(BookingStatus.PENDING)))
                .fetchOne()).orElse(0L);

        return count > 0;
    }
}
