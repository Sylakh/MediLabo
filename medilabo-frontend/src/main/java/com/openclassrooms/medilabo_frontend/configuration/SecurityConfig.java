package com.openclassrooms.medilabo_frontend.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {

	/*
	 * @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception { http.authorizeHttpRequests(authz ->
	 * authz.requestMatchers("/public/**").permitAll() // Accès public à certaines
	 * // URLs .anyRequest().permitAll() // Exige une authentification pour toutes
	 * les autres requêtes ).oauth2Login(Customizer.withDefaults()); // Configurer
	 * OAuth2 login, si nécessaire
	 * 
	 * http.csrf(csrf -> csrf.disable()); // Désactiver CSRF, à réactiver en
	 * production si nécessaire
	 * 
	 * return http.build(); }
	 * 
	 * @Bean JwtDecoder jwtDecoder() { // Configuration du décodeur JWT, par exemple
	 * return
	 * JwtDecoders.fromIssuerLocation("http://localhost:8080/realms/medilabo"); }
	 * 
	 * @Bean JwtAuthenticationProvider jwtAuthenticationProvider(JwtDecoder
	 * jwtDecoder) { return new JwtAuthenticationProvider(jwtDecoder); }
	 */

}