package com.openclassrooms.medilabo_frontend.proxies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.medilabo_frontend.service.KeycloakTokenService;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

	@Autowired
	private KeycloakTokenService keycloakTokenService; // Service pour récupérer le token

	@Override
	public void apply(RequestTemplate requestTemplate) {
		String accessToken = keycloakTokenService.getAccessToken(); // Méthode pour récupérer le token
		requestTemplate.header("Authorization", "Bearer " + accessToken);
		System.out.println("Bearer " + accessToken);
	}
}