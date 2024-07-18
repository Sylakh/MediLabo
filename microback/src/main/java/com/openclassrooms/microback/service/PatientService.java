package com.openclassrooms.microback.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.microback.modele.Patient;
import com.openclassrooms.microback.repository.PatientRepository;

@Service
public class PatientService {

	private static final Logger logger = LogManager.getLogger("PatientService");

	@Autowired
	private PatientRepository patientRepository;

	public Patient add(Patient patient) {
		logger.info("Create a new patient");
		return patientRepository.save(patient);
	}

	public Patient update(Patient patient, int id) throws Exception {
		Optional<Patient> optionalPatient = patientRepository.findById((long) id);
		if (optionalPatient.isPresent()) {
			Patient patientToBeUpdated = optionalPatient.get();
			patientToBeUpdated.setFirstName(patient.getFirstName());
			patientToBeUpdated.setLastName(patient.getLastName());
			patientToBeUpdated.setBirthday(patient.getBirthday());
			patientToBeUpdated.setAddress(patient.getAddress());
			patientToBeUpdated.setPhone(patient.getPhone());
			patientToBeUpdated.setGender(patient.getGender());
			return patientRepository.save(patientToBeUpdated);
		} else {
			throw new Exception("Patient not found");
		}

	}

}
