package com.openclassrooms.medilabo_note.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${spring.security.oauth2.ressourceserver.jwt.jwk-set-uri}")
	private String jwt;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(
						ar -> ar.requestMatchers("/actuator/**").permitAll().anyRequest().authenticated())
				// .csrf(AbstractHttpConfigurer::disable)
				.oauth2ResourceServer(o2 -> o2.jwt(Customizer.withDefaults())).build();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri(jwt).build();
	}

}