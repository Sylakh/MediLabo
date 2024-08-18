package com.openclassrooms.medilabo_report.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	RestTemplate restTemplate(BearerTokenRelayInterceptor bearerTokenRelayInterceptor) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(bearerTokenRelayInterceptor);
		return restTemplate;
	}
}
