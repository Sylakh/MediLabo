package com.openclassrooms.medilabo_frontend.proxies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.medilabo_frontend.service.KeycloakTokenService;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

	@Autowired
	private KeycloakTokenService keycloakTokenService;

	private String currentToken;
	private long tokenExpirationTime; // Stockage de l'heure d'expiration du token en millisecondes

	@Override
	public void apply(RequestTemplate template) {
		// Vérifier si le token est expiré ou inexistant
		if (currentToken == null || System.currentTimeMillis() >= tokenExpirationTime) {
			// Récupérer un nouveau token et mettre à jour l'heure d'expiration
			currentToken = keycloakTokenService.getAccessToken();
			tokenExpirationTime = keycloakTokenService.getTokenExpirationTime();
		}

		// Ajouter le token à l'en-tête Authorization
		template.header("Authorization", "Bearer " + currentToken);
	}
}
