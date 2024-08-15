package com.openclassrooms.gateway_server.configuration;

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

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(exchanges -> exchanges.pathMatchers("/microback/**", "/medilabo-note/**").authenticated()
				.anyExchange().permitAll())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder())));

		return http.build();
	}

	@Bean
	ReactiveJwtDecoder jwtDecoder() {
		String issuerUri = "http://localhost:8080/realms/medilabo";
		return NimbusReactiveJwtDecoder.withJwkSetUri(issuerUri + "/protocol/openid-connect/certs").build();
	}

	/*
	 * @Bean SecurityWebFilterChain springSecurityFilterChain( ServerHttpSecurity
	 * http , ServerLogoutSuccessHandler handler ) { http.authorizeExchange(exchange
	 * -> exchange.pathMatchers("/actuator/**", "/", "/logout.html").permitAll()
	 * .anyExchange().authenticated()).oauth2Login(Customizer.withDefaults()) // to
	 * redirect to // OAuth2 login .csrf(csrfSpec -> csrfSpec.disable()) // // page
	 * // .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) //
	 * // Using // JWT // authentication ) // // .logout(logout ->
	 * logout.logoutSuccessHandler(handler)) // Handling logout // //success // //
	 * Désactiver CSRF ici; ;
	 * 
	 * return http.build(); }
	 * 
	 * @Bean OAuth2AuthorizedClientService
	 * authorizedClientService(ClientRegistrationRepository
	 * clientRegistrationRepository) { return new
	 * InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository); }
	 */
	/*
	 * @Bean ClientRegistrationRepository clientRegistrationRepository() { return
	 * new InMemoryClientRegistrationRepository(yourClientRegistration()); }
	 * 
	 * private ClientRegistration yourClientRegistration() { return
	 * ClientRegistration.withRegistrationId("keycloack").clientId(
	 * "medilabo-client")
	 * .clientSecret("CPzskaE23byGd05bZx3CTyJ1yex2qJyw").scope("openid", "profile",
	 * "email") .authorizationUri(
	 * "http://localhost:8080/realms/medilabo/protocol/openid-connect/auth")
	 * .tokenUri(
	 * "http://localhost:8080/realms/medilabo/protocol/openid-connect/token")
	 * .userInfoUri(
	 * "http://localhost:8080/realms/medilabo/protocol/openid-connect/userinfo")
	 * .userNameAttributeName(IdTokenClaimNames.SUB).clientName("Keycloack")
	 * .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	 * .redirectUri("http://localhost:9102/login/oauth2/code/medilabo-client").build
	 * (); }
	 */
	/*
	 * @Bean ServerLogoutSuccessHandler
	 * keycloakLogoutSuccessHandler(ReactiveClientRegistrationRepository repository)
	 * {
	 * 
	 * OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler = new
	 * OidcClientInitiatedServerLogoutSuccessHandler( repository);
	 * 
	 * oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/logout.html");
	 * 
	 * return oidcLogoutSuccessHandler; }
	 */

	/*
	 * @Bean ReactiveJwtDecoder reactiveJwtDecoder() { return
	 * ReactiveJwtDecoders.fromIssuerLocation(issuerUri); }
	 */
}
