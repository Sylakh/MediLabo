package com.openclassrooms.medilabo_frontend.service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KeycloakTokenService {

	private final ConcurrentHashMap<String, CachedToken> tokenCache = new ConcurrentHashMap<>();

	public String getAccessToken() {
		CachedToken cachedToken = tokenCache.get("accessToken");

		if (cachedToken == null || cachedToken.isExpired()) {
			// Récupérer un nouveau token depuis Keycloak
			String newToken = fetchNewToken();
			Instant expiryTime = Instant.now().plusSeconds(600); // le token expire dans 600 secondes

			cachedToken = new CachedToken(newToken, expiryTime);
			tokenCache.put("accessToken", cachedToken);
		}

		return cachedToken.getToken();
	}

	private String fetchNewToken() {
		// Implémentez la logique pour récupérer un nouveau jeton à partir de Keycloak
		// Cette méthode doit retourner le jeton d'accès

		// Créer une instance de RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// Définir l'URL cible
		String url = "http://localhost:8080/realms/medilabo/protocol/openid-connect/token";

		// Définir les headers de la requête
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Définir les paramètres du corps (body) sous forme de MultiValueMap
		MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
		bodyParams.add("client_id", "medilabo-client");
		bodyParams.add("username", "user1");
		bodyParams.add("password", "password");
		bodyParams.add("grant_type", "password");
		bodyParams.add("client_secret", "CPzskaE23byGd05bZx3CTyJ1yex2qJyw");

		// Créer un HttpEntity avec les headers et le corps
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);

		// Envoyer la requête POST
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

		// Récupérer le body de la réponse en tant que chaîne JSON
		String responseBody = response.getBody();

		try {
			// Utiliser Jackson pour parser la réponse JSON
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(responseBody);

			// Extraire l'access_token du JSON
			String accessToken = jsonNode.get("access_token").asText();

			// Afficher l'access_token
			System.out.println("Access Token: " + accessToken);

			return accessToken;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "nouveau_token"; // Remplacez par la logique réelle pour obtenir le jeton

	}

	private static class CachedToken {
		private final String token;
		private final Instant expiryTime;

		public CachedToken(String token, Instant expiryTime) {
			this.token = token;
			this.expiryTime = expiryTime;
		}

		public String getToken() {
			return token;
		}

		public boolean isExpired() {
			return Instant.now().isAfter(expiryTime);
		}
	}
}
