# Flight Booking Microservices Platform ✈️

This project is a Flight Booking System built using a microservices architecture. Each service is responsible for a specific business domain, making the system easier to maintain and extend.

## Services

- **Flight Service**
  - Manages flights, schedules, airports, seat availability, and flight status.

- **Booking Service**
  - Handles flight reservations and booking management.

- **Passenger Service**
  - Manages passenger information and user accounts.

---

## Flight Service Features

- Create, update, retrieve, and cancel flights.
- Manage seat availability through reservation and release operations.
- Validate flight data, including airport selection, flight times, and unique flight numbers.
- Prevent reducing total seat capacity below the number of reserved seats.
- Use soft deletion by marking flights as `CANCELLED`.
- Separate entities from API models using DTOs and ModelMapper.
- Use transactional service methods to ensure data consistency.
- Dynamic flight search using QueryDSL with multiple optional filters:
  - Departure airport
  - Arrival airport
  - Flight status
  - Departure date
  - Maximum price
- Use fetch join optimization to reduce unnecessary database queries.
- Retrieve cheapest available flights using price-based sorting.

---

## Booking Service Features

- Create, update, retrieve, and cancel bookings.
- Confirm booking payment and update booking status.
- Validate passenger and flight existence through OpenFeign clients.
- Prevent multiple pending bookings for the same passenger.
- Automatically reserve and release flight seats.
- Recalculate booking price when the flight or seat count changes.
- Generate unique booking references.
- Search bookings dynamically using QueryDSL with optional filters:
  - Passenger ID
  - Flight ID
  - Booking reference
  - Booking status
  - Payment status
  - Booking date range
- Use DTOs, ModelMapper, and transactional service methods.
- Centralized exception handling with custom business exceptions.

---

## Passenger Service Features

- Create, update, retrieve, and delete passengers.
- Validate passenger information.
- Separate entities from API models using DTOs and ModelMapper.
- Global exception handling.

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
- Lombok
- Maven
- Docker (planned)

---

## Running the Project

Each service runs independently.

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

> Docker and Docker Compose support will be added to simplify running all services together.
