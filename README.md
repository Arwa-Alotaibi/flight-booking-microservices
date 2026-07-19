#  Flight Booking Microservices Platform ✈️

This project is a Flight Booking System built using a microservices architecture. Each service is responsible for a specific business domain, making the system easier to maintain and extend.

## Services

- **Flight Service** (Completed)
  - Manages flights, schedules, airports, seat availability, and flight status.

- **Booking Service** (In Progress)
  - Handles flight reservations and booking management.

- **Passenger Service** (Planned)
  - Manages passenger information and user accounts.

## Flight Service Features

- Create, update, retrieve, and cancel flights.
- Manage seat availability through reservation and release operations.
- Validate flight data, including airport selection, flight times, and unique flight numbers.
- Prevent reducing total seat capacity below the number of reserved seats.
- Use soft deletion by marking flights as `CANCELLED`.
- Separate entities from API models using DTOs and ModelMapper.
- Use transactional service methods to ensure data consistency.

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- ModelMapper
- Lombok
- Maven


## Running the Flight Service

```bash
cd flight-service
mvn spring-boot:run
```
