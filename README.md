# Flight Booking Microservices ✈️

A simple Flight Booking System built using a microservices architecture. The project is split into independent services, where each service is responsible for a specific business domain.

## Services

- **Flight Service** – Manages flights, schedules, seat availability, and flight status.
- **Booking Service** – Manages reservations and booking operations.
- **Passenger Service** – Manages passenger information.

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
```
