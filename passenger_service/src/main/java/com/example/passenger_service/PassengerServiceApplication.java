package com.example.passenger_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PassengerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassengerServiceApplication.class, args);
	}

}
