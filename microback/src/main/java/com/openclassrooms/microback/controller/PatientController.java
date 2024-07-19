package com.openclassrooms.microback.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
