package com.example.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingSearchResponse {
    List<BookingResponseDto> bookingResponseDtoList;
    private Long countSearch;


}
