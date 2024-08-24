package com.openclassrooms.medilabo_frontend.service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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
import com.openclassrooms.medilabo_frontend.model.UserData;

@Service
public class KeycloakTokenService {

	private static final Logger logger = LogManager.getLogger("KeycloakTokenService");

	private UserData userData;

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	private final ConcurrentHashMap<String, CachedToken> tokenCache = new ConcurrentHashMap<>();

	// URL cible
	@Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
	private String urlToken;

	@Value("${keycloak.token-revoke-uri}")
	private String urlRevokeToken;

	@Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
	private String clientSecret;

	// Ajouter un attribut pour stocker l'heure d'expiration du token
	private long tokenExpirationTime;

	public String getAccessToken() {
		logger.info("Get access token");
		CachedToken cachedToken = tokenCache.get("accessToken");

		long expiresIn = 600; // Exemple de valeur
		tokenExpirationTime = System.currentTimeMillis() + expiresIn * 1000;

		if (cachedToken == null || cachedToken.isExpired()) {
			// Récupérer un nouveau token depuis Keycloak
			String newToken = fetchNewToken();
			Instant expiryTime = Instant.now().plusSeconds(expiresIn); // le token expire dans 600 secondes

			cachedToken = new CachedToken(newToken, expiryTime);
			tokenCache.put("accessToken", cachedToken);
		}
		System.out.println("access token: " + cachedToken.getToken());
		return cachedToken.getToken();
	}

	public long getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	private String fetchNewToken() {

		logger.info("Get new access token");

		// Créer une instance de RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// Définir les headers de la requête
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Définir les paramètres du corps (body) sous forme de MultiValueMap
		MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
		bodyParams.add("client_id", clientId);
		bodyParams.add("username", userData.getUsername());
		bodyParams.add("password", userData.getPassword());
		bodyParams.add("grant_type", "password");
		bodyParams.add("client_secret", clientSecret);

		// Créer un HttpEntity avec les headers et le corps
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);

		// Envoyer la requête POST
		ResponseEntity<String> response = restTemplate.exchange(urlToken, HttpMethod.POST, requestEntity, String.class);

		// Récupérer le body de la réponse en tant que chaîne JSON
		String responseBody = response.getBody();

		try {
			// Utiliser Jackson pour parser la réponse JSON
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(responseBody);

			// Extraire l'access_token du JSON
			String accessToken = jsonNode.get("access_token").asText();

			return accessToken;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "nouveau_token";

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

	public void logout() {
		String accessToken = tokenCache.get("accessToken") != null ? tokenCache.get("accessToken").getToken() : null;

		// Vider le cache des tokens
		tokenCache.clear();
		System.out.println("All tokens have been cleared.");

		if (accessToken != null) {
			revokeToken(accessToken);
		}
	}

	private void revokeToken(String accessToken) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
		bodyParams.add("token", accessToken);
		bodyParams.add("client_id", clientId);
		bodyParams.add("client_secret", clientSecret);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(urlRevokeToken, HttpMethod.POST, requestEntity,
					String.class);
			if (response.getStatusCode().is2xxSuccessful()) {
				System.out.println("Token has been successfully revoked.");
			} else {
				System.out.println("Failed to revoke token. Status code: " + response.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error occurred while revoking the token.");
		} finally {

		}
	}

	public boolean isUserDataValid(UserData userData) {
		if (userData == null) {
			return false;
		}
		if (userData.getUsername() == null || userData.getUsername().isEmpty()) {
			return false;
		}
		if (userData.getPassword() == null || userData.getPassword().isEmpty()) {
			return false;
		}
		return true;
	}

}
