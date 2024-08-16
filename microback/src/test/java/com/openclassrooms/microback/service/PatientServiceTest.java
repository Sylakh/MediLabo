package com.openclassrooms.microback.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.microback.exception.PatientNotFoundException;
import com.openclassrooms.microback.model.Patient;
import com.openclassrooms.microback.repository.PatientRepository;

public class PatientServiceTest {

	@Mock
	private PatientRepository patientRepository;

	@InjectMocks
	private PatientService patientService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddPatient() {
		Patient patient = new Patient();
		when(patientRepository.save(patient)).thenReturn(patient);
		Patient created = patientService.add(patient);
		assertEquals(patient, created);
		verify(patientRepository, times(1)).save(patient);
	}

	@Test
	public void testUpdatePatient_Success() throws Exception {
		Patient patient = new Patient();
		patient.setFirstName("John");
		Optional<Patient> optionalPatient = Optional.of(patient);

		when(patientRepository.findById(1)).thenReturn(optionalPatient);
		when(patientRepository.save(any(Patient.class))).thenReturn(patient);

		Patient updated = patientService.update(patient, 1);

		assertEquals("John", updated.getFirstName());
		verify(patientRepository, times(1)).findById(1);
		verify(patientRepository, times(1)).save(patient);
	}

	@Test
	public void testUpdatePatient_NotFound() {
		Patient patient = new Patient();
		when(patientRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(PatientNotFoundException.class, () -> {
			patientService.update(patient, 1);
		});

		assertEquals("Patient not found", exception.getMessage());
	}

	@Test
	public void testGetAllPatients() {
		Patient patient1 = new Patient();
		Patient patient2 = new Patient();
		List<Patient> patients = Arrays.asList(patient1, patient2);

		when(patientRepository.findAll()).thenReturn(patients);

		List<Patient> result = patientService.getAllPatent();

		assertEquals(2, result.size());
		verify(patientRepository, times(1)).findAll();
	}

	@Test
	public void testGetPatientById_Success() throws Exception {
		Patient patient = new Patient();
		when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

		Patient result = patientService.getPatientById(1);

		assertEquals(patient, result);
		verify(patientRepository, times(1)).findById(1);
	}

	@Test
	public void testGetPatientById_NotFound() {
		when(patientRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(PatientNotFoundException.class, () -> {
			patientService.getPatientById(1);
		});

		assertEquals("Patient not found", exception.getMessage());
	}
}
