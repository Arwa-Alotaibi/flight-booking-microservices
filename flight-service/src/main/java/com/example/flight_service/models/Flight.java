package com.example.flight_service.models;

import com.example.flight_service.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightId;

    @Column(name="flightNumber",unique = true,nullable = false)
    private String  flightNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departureAirportId",referencedColumnName = "airportId")
    private Airport departureAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrivalAirportId",referencedColumnName = "airportId")
    private Airport arrivalAirport;

    @Column(name = "departureTime",nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrivalTime",nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name="availableSeats",nullable = false)
    private Integer availableSeats;

    @Column(name="totalSeats",nullable = false)
    private Integer totalSeats;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @CreationTimestamp
    @Column( updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column( nullable = false)
    private LocalDateTime updatedAt;

}
