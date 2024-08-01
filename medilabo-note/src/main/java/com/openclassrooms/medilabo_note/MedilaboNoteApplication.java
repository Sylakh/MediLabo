package com.openclassrooms.medilabo_note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MedilaboNoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedilaboNoteApplication.class, args);
	}

}
