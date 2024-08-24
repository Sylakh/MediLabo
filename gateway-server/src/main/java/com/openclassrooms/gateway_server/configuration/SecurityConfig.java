package com.openclassrooms.gateway_server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Value("${spring.security.oauth2.ressourceserver.jwt.issuer-uri}")
	private String issuerUri;

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(
				exchanges -> exchanges.pathMatchers("/actuator/**").permitAll().anyExchange().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder())));

		return http.build();
	}

	@Bean
	ReactiveJwtDecoder jwtDecoder() {
		return NimbusReactiveJwtDecoder.withJwkSetUri(issuerUri + "/protocol/openid-connect/certs").build();
	}

}
