package com.openclassrooms.medilabo_report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.openclassrooms.medilabo_report.model.PatientNoteBeans;

@Service
public class NoteService {

	@Autowired
	private RestTemplate restTemplate;

	public String helloFromNote() {
		String url = "http://localhost:9003/hello";
		return restTemplate.getForObject(url, String.class);
	}

	public List<PatientNoteBeans> getAllNotes() {
		String url = "http://localhost:9003/patientnote";

		// Utilisation de ParameterizedTypeReference pour définir le type de retour
		ResponseEntity<List<PatientNoteBeans>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PatientNoteBeans>>() {
				});

		// Récupération du corps de la réponse
		return response.getBody();
	}
}
