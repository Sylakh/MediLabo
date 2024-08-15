package com.openclassrooms.medilabo_frontend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private ClientRegistrationRepository clientRegistrationRepository;

	public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
		this.clientRegistrationRepository = clientRegistrationRepository;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/login").permitAll();
			auth.requestMatchers("/").permitAll();
			auth.anyRequest().authenticated();
		}).oauth2Login(Customizer.withDefaults())
				.logout(logout -> logout.logoutSuccessUrl("/").logoutSuccessHandler(oidcLogoutSuccessHandler())
						.clearAuthentication(true).permitAll().deleteCookies("JSESSIONID"))
				.csrf(Customizer.withDefaults());

		return http.build();
	}

	private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
		final OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(
				this.clientRegistrationRepository);
		oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}?logoutsuccess=true");
		return oidcLogoutSuccessHandler;
	}
}
