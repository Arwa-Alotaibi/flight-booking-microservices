# Flight Booking Microservices ✈️

A flight booking system built using Java , Spring Boot, and Microservices architecture.

The application is split into multiple services that communicate through REST APIs and asynchronous events.

---

## Services
* **Eureka Server:** Used for service discovery and service registration.
* **API Gateway:** Routes requests to the different services.
* **Flight Service:** Handles flight creation, scheduling, seat availability, and flight searching using QueryDSL.
* **Passenger Service:** Manages passenger profiles and basic information.
* **Booking Service:** Manages flight reservations, calculates booking prices, manages seat availability, and handles booking status.
* **Payment Service:** Creates and processes payments and manages payment status (`UNPAID` / `PAID` / `REFUNDED`).
* **Notification Service:** Consumes payment success events from Kafka.

---

## Inter-Service Communication

* **Synchronous (REST / OpenFeign):**  
  `Booking Service` communicates with `Flight`, `Passenger`, and `Payment` services using **Spring Cloud OpenFeign** for validations and requests.

* **Asynchronous (Apache Kafka):**  
  When a payment is successfully processed in `Payment Service`, a `PaymentSuccessEvent` is published to the `payment-success` Kafka topic.

  `Notification Service` consumes the event and handles the payment notification.

---
## Service Discovery

The services are registered with **Eureka Server** and discovered using their service names.

The API Gateway uses Eureka to route requests to the appropriate service.

---
## Tech Stack

- Java 
- Spring Boot
- Spring Data JPA
- Spring Cloud OpenFeign
- QueryDSL
- Hibernate
- MySQL
- ModelMapper
- Maven
- Apache Kafka
- Spring for Apache Kafka
---

