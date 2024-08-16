package com.openclassrooms.medilabo_frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.openclassrooms")
public class MicrofrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrofrontApplication.class, args);

	}
}
