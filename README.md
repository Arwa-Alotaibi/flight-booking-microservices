# Flight Booking Microservices ✈️

A flight booking system built with Java, Spring Boot.

The project is divided into multiple services, with each service handling a specific part of the booking process. The services communicate using REST APIs and asynchronous events.

## Services

* **Eureka Server:** Used for service discovery and service registration.
* **API Gateway:** Routes requests to the different services.
* **Flight Service:** Handles flight creation, updates, cancellation, flight search, cheapest flights, and seat availability.
* **Passenger Service:** Handles creating, retrieving, updating, and deleting passengers.
* **Booking Service:** Handles booking creation, updates, cancellation, booking search, price calculation, seat availability, and payment processing.
* **Payment Service:** Handles payment creation, retrieval, and processing. It also publishes a payment success event to Kafka after a successful payment.
* **Notification Service:** Listens to the `payment-success` Kafka topic and receives payment success events.

## API Documentation
The project includes a Postman collection containing the available API endpoints.
Requests can be sent through the API Gateway.
Postman Documentation:
[View Postman Documentation](https://documenter.getpostman.com/view/25472571/2sBYArVtKi#649d7117-a8ec-4fe2-9c52-9da90524cf19)

## Communication Between Services

### Synchronous – REST / OpenFeign
Booking Service communicates with Flight, Passenger, and Payment services using Spring Cloud OpenFeign.

### Asynchronous – Apache Kafka
After a payment is successfully processed, Payment Service publishes a `PaymentSuccessEvent` to the `payment-success` Kafka topic. Notification Service consumes this event and reads the payment details.

## Testing

Unit tests were added for:
* Flight Service
* Booking Service
* Payment Service

Testing was done using **JUnit** and **Mockito**.

## Tech Stack

* Java
* Spring Boot
* Spring Data JPA
* Spring Cloud OpenFeign
* QueryDSL
* Hibernate
* MySQL
* ModelMapper
* Maven
* Apache Kafka
* Spring for Apache Kafka
* JUnit
* Mockito
