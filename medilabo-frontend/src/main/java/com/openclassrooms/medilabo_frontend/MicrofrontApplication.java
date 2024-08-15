package com.openclassrooms.medilabo_frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableFeignClients("com.openclassrooms")
public class MicrofrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrofrontApplication.class, args);

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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
