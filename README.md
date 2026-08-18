# Flight Booking Microservices ✈️

A flight booking system built using Java , Spring Boot, and Microservices architecture.

The application is split into multiple services that communicate through REST APIs and asynchronous events.

---

## Services

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

```bash
cd payment_service
mvn spring-boot:run
```

