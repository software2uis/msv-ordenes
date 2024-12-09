package com.software2uis.msv_ordenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrdenesMicroservicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdenesMicroservicioApplication.class, args);
	}

}
