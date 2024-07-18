package com.openclassrooms.microback.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.microback.modele.Patient;
import com.openclassrooms.microback.service.PatientService;

@RestController
public class PatientController {

	private static final Logger logger = LogManager.getLogger("PatientController");

	@Autowired
	private PatientService patientService;

	@PostMapping("/patient")
	public Patient addPatient(@RequestBody Patient patient) {
		logger.info("Create a new patient");
		return patientService.add(patient);
	}

	@PutMapping("/patient/{id}")
	public Patient updatePatient(@RequestBody Patient patient, @PathVariable("id") int id) throws Exception {
		logger.info("Update patient id: " + id);
		return patientService.update(patient, id);
	}

}
