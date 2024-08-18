package com.openclassrooms.medilabo_report.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.medilabo_report.constant.RiskLevel;
import com.openclassrooms.medilabo_report.model.ReportData;

public class PatientDataAnalyzerTest {

	private ReportData reportData;

	@BeforeEach
	public void setUp() {
		reportData = new ReportData();
	}

	@Test
	public void testAnalyzePatientData_NoTriggers_NoRisk() {
		reportData.setNotes(Arrays.asList("Nothing important here"));
		reportData.setAge(40);
		reportData.setSex("M");

		PatientDataAnalyzer.analyzePatientData(reportData);

		assertEquals(RiskLevel.NONE.name(), reportData.getResult());
	}

	@Test
	public void testAnalyzePatientData_AgeAbove30_BorderlineRisk() {
		reportData.setNotes(Arrays.asList("Hémoglobine A1C niveau élevé", "Poids anormal"));
		reportData.setAge(45);
		reportData.setSex("F");

		PatientDataAnalyzer.analyzePatientData(reportData);

		assertEquals(RiskLevel.BORDERLINE.name(), reportData.getResult());
	}

	@Test
	public void testAnalyzePatientData_AgeAbove30_InDangerRisk() {
		reportData.setNotes(Arrays.asList("Taille incorrecte", "Poids anormal", "Fumeur", "Cholestérol élevé",
				"Vertiges fréquents", "Microalbumine détectée"));
		reportData.setAge(50);
		reportData.setSex("M");

		PatientDataAnalyzer.analyzePatientData(reportData);

		assertEquals(RiskLevel.IN_DANGER.name(), reportData.getResult());
	}

	@Test
	public void testAnalyzePatientData_AgeAbove30_EarlyOnsetRisk() {
		reportData.setNotes(Arrays.asList("Taille incorrecte", "Poids anormal", "Fumeur", "Cholestérol élevé",
				"Vertiges fréquents", "Microalbumine détectée", "Hémoglobine A1C niveau élevé", "Rechute possible"));
		reportData.setAge(60);
		reportData.setSex("F");

		PatientDataAnalyzer.analyzePatientData(reportData);

		assertEquals(RiskLevel.EARLY_ONSET.name(), reportData.getResult());
	}

	@Test
	public void testAnalyzePatientData_AgeBelow30_InDangerRiskForMale() {
		reportData.setNotes(Arrays.asList("Fumeur", "Cholestérol élevé", "Hémoglobine A1C niveau élevé"));
		reportData.setAge(25);
		reportData.setSex("M");

		PatientDataAnalyzer.analyzePatientData(reportData);

		assertEquals(RiskLevel.IN_DANGER.name(), reportData.getResult());
	}

	@Test
	public void testAnalyzePatientData_AgeBelow30_EarlyOnsetRiskForMale() {
		reportData.setNotes(Arrays.asList("Fumeur", "Cholestérol élevé", "Hémoglobine A1C niveau élevé",
				"Rechute possible", "Microalbumine détectée"));
		reportData.setAge(25);
		reportData.setSex("M");

		PatientDataAnalyzer.analyzePatientData(reportData);

		assertEquals(RiskLevel.EARLY_ONSET.name(), reportData.getResult());
	}

	@Test
	public void testAnalyzePatientData_AgeBelow30_InDangerRiskForFemale() {
		reportData.setNotes(
				Arrays.asList("Fumeuse", "Cholestérol élevé", "Hémoglobine A1C niveau élevé", "Rechute possible"));
		reportData.setAge(25);
		reportData.setSex("F");

		PatientDataAnalyzer.analyzePatientData(reportData);

		assertEquals(RiskLevel.IN_DANGER.name(), reportData.getResult());
	}

	@Test
	public void testAnalyzePatientData_AgeBelow30_EarlyOnsetRiskForFemale() {
		reportData.setNotes(Arrays.asList("Fumeuse", "Cholestérol élevé", "Hémoglobine A1C niveau élevé",
				"Rechute possible", "Microalbumine détectée", "Vertiges fréquents", "Anticorps détectés"));
		reportData.setAge(25);
		reportData.setSex("F");

		PatientDataAnalyzer.analyzePatientData(reportData);

		assertEquals(RiskLevel.EARLY_ONSET.name(), reportData.getResult());
	}
}
