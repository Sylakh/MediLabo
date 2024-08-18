package com.openclassrooms.medilabo_report.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.medilabo_report.model.PatientBeans;
import com.openclassrooms.medilabo_report.model.PatientNoteBeans;
import com.openclassrooms.medilabo_report.model.ReportData;

@Service
public class ReportDataService {

	public List<ReportData> generateReports(List<PatientBeans> patients, List<PatientNoteBeans> notes) {
		// Convertir la liste des notes en une map pour un accès plus rapide

		// Utiliser String comme type de clé si getPatientId() retourne un String
		Map<String, List<PatientNoteBeans>> notesByPatientId = notes.stream()
				.collect(Collectors.groupingBy(PatientNoteBeans::getPatientId));
		// Construire la liste de ReportData
		List<ReportData> reports = new ArrayList<>();

		for (PatientBeans patient : patients) {
			// Récupérer les notes associées à ce patient
			String key = integerToString(patient.getId());
			List<PatientNoteBeans> patientNotes = notesByPatientId.get(key);

			// Si ce patient a des notes associées, créer un ReportData
			if (patientNotes != null) {
				for (PatientNoteBeans note : patientNotes) {
					ReportData report = new ReportData();
					// Remplir les champs de ReportData avec les données de PatientBeans et
					// PatientNoteBeans
					report.setId((long) patient.getId());
					report.setSex(patient.getGender());
					report.setAge(calculateAge(patient.getBirthday()));
					report.setNotes(note.getPatientNotes());
					// Ajouter le ReportData à la liste
					reports.add(report);
				}
			}
		}

		return reports;
	}

	public static int calculateAge(Date birthDate) {
		if (birthDate == null) {
			throw new IllegalArgumentException("La date de naissance ne doit pas être null");
		}

		// Convertir java.sql.Date en java.time.LocalDate
		LocalDate birthLocalDate = birthDate.toLocalDate();

		// Obtenir la date actuelle
		LocalDate currentDate = LocalDate.now();

		// Calculer la différence entre la date actuelle et la date de naissance
		return Period.between(birthLocalDate, currentDate).getYears();
	}

	/**
	 * Convertit un entier en chaîne de caractères.
	 * 
	 * @param input L'entier à convertir.
	 * @return La chaîne de caractères représentant l'entier.
	 */
	public static String integerToString(Integer input) {
		if (input == null) {
			return null; // Retourne null si l'input est null pour gérer les valeurs nulles
		}
		return input.toString();
	}

	/**
	 * Convertit une chaîne de caractères en un entier.
	 * 
	 * @param input La chaîne de caractères à convertir.
	 * @return L'entier converti, ou null si la conversion échoue.
	 */
	public static Integer stringToInteger(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.err.println("Erreur: La chaîne de caractères n'est pas un entier valide - " + input);
			return null;
		}
	}

}
