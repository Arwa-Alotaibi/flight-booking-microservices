# Flight Booking Microservices

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
- Manage available seats when bookings are created or cancelled.
- Prevent invalid flight data such as duplicate flight numbers or invalid departure and arrival times.
- Use soft deletion by marking cancelled flights instead of removing them from the database.
- Separate entities from API models using DTOs and ModelMapper.
- Use transactional service methods for data consistency.

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
