package br.com.javaguides.email_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServiceApplication {
// aplicação que recebe order event de order service
	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}

}
