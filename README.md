# Flight Booking Microservices ✈️

A Flight Booking System developed with Spring Boot following a microservices architecture. The application is divided into Flight, Booking, and Passenger services, with each service responsible for a specific business domain and communicating through REST APIs.

## Services

- **Flight Service** – Manages flights, schedules, seat availability, and flight status.
- **Booking Service** – Manages reservations and booking operations.
- **Passenger Service** – Manages passenger information.
- **Payment Service** – Processes payment requests and publishes payment success events.

---

## Features

### Flight Service
- Create, update, retrieve, and cancel flights.
- Manage seat availability.
- Search flights using QueryDSL.
- Retrieve the cheapest available flights.

### Booking Service
- Create, update, retrieve, and cancel bookings.
- Confirm booking payments.
- Reserve and release seats through OpenFeign.
- Prevent duplicate pending bookings.
- Search bookings using QueryDSL.

### Passenger Service
- Create, update, retrieve, and delete passengers.

### Payment Service
Create payment requests for pending bookings.
Process payments securely and validate status transitions.
Publish PaymentSuccessEvent to Kafka upon successful transactions. 

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Cloud OpenFeign
- QueryDSL
- Hibernate
- MySQL
- ModelMapper
- Maven

---

## Run Services

```bash
cd passenger_service
mvn spring-boot:run
```

```bash
cd flight-service
mvn spring-boot:run
```

```bash
cd booking_service
mvn spring-boot:run

```bash
cd payment_service
mvn spring-boot:run 


```
