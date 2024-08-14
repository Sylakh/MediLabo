package com.openclassrooms.gateway_server.configuration;

import org.springframework.stereotype.Controller;

@Controller
public class TokenController {

	/*
	 * private final OAuth2AuthorizedClientService clientService;
	 * 
	 * public TokenController(OAuth2AuthorizedClientService clientService) {
	 * this.clientService = clientService; }
	 * 
	 * @GetMapping("/token") public String getToken(OAuth2AuthenticationToken
	 * authentication) { OAuth2AuthorizedClient client = clientService
	 * .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(),
	 * authentication.getName()); return "Token: " +
	 * client.getAccessToken().getTokenValue(); }
	 */
}
