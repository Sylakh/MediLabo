package com.openclassrooms.microback.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.openclassrooms.microback.model.Patient;
import com.openclassrooms.microback.service.PatientService;

import jakarta.validation.Valid;

@RestController
public class PatientController {

	private static final Logger logger = LogManager.getLogger("PatientController");

	@Autowired
	private PatientService patientService;

	@GetMapping("/hello")
	public String hello() {
		return "Hello from Microback!";
	}

	@PostMapping("/patient")
	public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) {
		logger.info("Create a new patient");
		Patient patientSaved = patientService.add(patient);
		if (Objects.isNull(patientSaved)) {
			return ResponseEntity.noContent().build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(patientSaved.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/patient/{id}")
	public Patient updatePatient(@Valid @RequestBody Patient patient, @PathVariable("id") int id) throws Exception {
		logger.info("Update patient id: " + id);
		return patientService.update(patient, id);
	}

	@GetMapping("/patient")
	public List<Patient> getAllPatient() {
		logger.info("get list of all patients");
		return patientService.getAllPatent();
	}

	@GetMapping("/patient/{id}")
	public Patient findPatientById(@PathVariable("id") int id) throws Exception {
		logger.info("get patient id: " + id);
		return patientService.getPatientById(id);
	}

	@DeleteMapping("/patient/{id}")
	public void deletePatientById(@PathVariable("id") int id) throws Exception {
		logger.info("delete patient id: " + id);
		patientService.delete(id);
	}

}
