package com.openclassrooms.medilabo_report.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.medilabo_report.model.PatientBeans;
import com.openclassrooms.medilabo_report.model.PatientNoteBeans;
import com.openclassrooms.medilabo_report.model.ReportData;

public class ReportDataServiceTest {

	private ReportDataService reportDataService;

	@BeforeEach
	public void setUp() {
		reportDataService = new ReportDataService();
	}

	@Test
	public void testGenerateReports_SuccessfulGeneration() throws Exception {
		// Mock des données de patients
		PatientBeans patient1 = new PatientBeans();
		patient1.setId(1);
		patient1.setGender("M");
		patient1.setBirthday(Date.valueOf(LocalDate.of(1980, 1, 1)));

		PatientBeans patient2 = new PatientBeans();
		patient2.setId(2);
		patient2.setGender("F");
		patient2.setBirthday(Date.valueOf(LocalDate.of(1990, 6, 15)));

		List<PatientBeans> patients = Arrays.asList(patient1, patient2);

		// Mock des données de notes de patients
		PatientNoteBeans note1 = new PatientNoteBeans();
		note1.setPatientId("1");
		note1.setPatientNotes(Arrays.asList("Note1 for Patient1", "Note2 for Patient1"));

		PatientNoteBeans note2 = new PatientNoteBeans();
		note2.setPatientId("2");
		note2.setPatientNotes(Arrays.asList("Note1 for Patient2"));

		List<PatientNoteBeans> notes = Arrays.asList(note1, note2);

		// Exécution de la méthode à tester
		List<ReportData> reports = reportDataService.generateReports(patients, notes);

		// Vérifications
		assertEquals(2, reports.size());
		assertEquals(1L, reports.get(0).getId());
		assertEquals("M", reports.get(0).getSex());
		assertEquals(44, reports.get(0).getAge()); // Assuming the current year is 2024
		assertEquals(Arrays.asList("Note1 for Patient1", "Note2 for Patient1"), reports.get(0).getNotes());

		assertEquals(2L, reports.get(1).getId());
		assertEquals("F", reports.get(1).getSex());
		assertEquals(34, reports.get(1).getAge());
		assertEquals(Arrays.asList("Note1 for Patient2"), reports.get(1).getNotes());
	}

	@Test
	public void testGenerateReports_PatientWithoutNotes_ThrowsException() {
		// Mock des données de patients sans notes
		PatientBeans patient1 = new PatientBeans();
		patient1.setId(1);
		patient1.setGender("M");
		patient1.setBirthday(Date.valueOf(LocalDate.of(1980, 1, 1)));

		List<PatientBeans> patients = Arrays.asList(patient1);
		List<PatientNoteBeans> notes = new ArrayList<>(); // Pas de notes

		// Vérification de l'exception
		Exception exception = assertThrows(Exception.class, () -> {
			reportDataService.generateReports(patients, notes);
		});

		assertEquals("la liste est nulle!", exception.getMessage());
	}

	@Test
	public void testCalculateAge_ValidDate() {
		Date birthDate = Date.valueOf(LocalDate.of(1980, 1, 1));
		int age = ReportDataService.calculateAge(birthDate);
		assertEquals(44, age); // Assuming the current year is 2024
	}

	@Test
	public void testCalculateAge_NullDate_ThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			ReportDataService.calculateAge(null);
		});
	}
}
