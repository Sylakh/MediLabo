package com.openclassrooms.medilabo_report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.openclassrooms.medilabo_report.model.PatientBeans;

@Service
public class MicrobackService {

	@Autowired
	private RestTemplate restTemplate;

	public String helloFromMicroback() {
		String url = "http://localhost:9001/hello";
		return restTemplate.getForObject(url, String.class);
	}

	public List<PatientBeans> getAllPatients() {
		String url = "http://localhost:9001/patient";

		// Utilisation de ParameterizedTypeReference pour définir le type de retour
		ResponseEntity<List<PatientBeans>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PatientBeans>>() {
				});

		// Récupération du corps de la réponse
		return response.getBody();
	}
}
