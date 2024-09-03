package com.medilabo_solutions.diabete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DiabeteApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiabeteApplication.class, args);
	}

}
