package com.openclassrooms.microback.service;

import java.util.List;
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
		logger.info("process update patient begins");
		Optional<Patient> optionalPatient = patientRepository.findById(id);
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

	public List<Patient> getAllPatent() {
		logger.info("process get all patients begins");
		return patientRepository.findAll();
	}

	public Patient getPatientById(int id) throws Exception {
		logger.info("process get patient by id begins");
		Optional<Patient> optionalPatient = patientRepository.findById(id);
		if (optionalPatient.isPresent()) {
			return optionalPatient.get();
		} else {
			throw new Exception("Patient not found");
		}
	}

	public void delete(int id) throws Exception {
		logger.info("process get patient by id begins");
		Optional<Patient> optionalPatient = patientRepository.findById(id);
		if (optionalPatient.isPresent()) {
			patientRepository.deleteById(optionalPatient.get().getId());
		} else {
			throw new Exception("Patient not found");
		}
	}

}
