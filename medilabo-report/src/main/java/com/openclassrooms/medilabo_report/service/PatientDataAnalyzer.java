package com.openclassrooms.medilabo_report.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.openclassrooms.medilabo_report.constant.RiskLevel;
import com.openclassrooms.medilabo_report.constant.TriggerTerm;
import com.openclassrooms.medilabo_report.model.ReportData;

public class PatientDataAnalyzer {

	private static final Logger logger = LogManager.getLogger("PatientDataAnalyzer");

	private static final Set<String> triggers = Arrays.stream(TriggerTerm.values())
			.map(term -> term.getDescription().toLowerCase()) // Convertir les descriptions en majuscules
			.collect(Collectors.toSet());

	public static void analyzePatientData(ReportData reportData) {
		logger.info("Analyze Patient Data process begins!");
		int triggerCount = countTriggers(reportData.getNotes());

		logger.debug("trigger: " + triggerCount + " for patient: " + reportData.getId());

		if (triggerCount == 0) {
			reportData.setResult(RiskLevel.NONE.name());
		} else if (reportData.getAge() > 30) {
			if (triggerCount >= 2 && triggerCount <= 5) {
				reportData.setResult(RiskLevel.BORDERLINE.name());
			} else if (triggerCount >= 6 && triggerCount <= 7) {
				reportData.setResult(RiskLevel.IN_DANGER.name());
			} else if (triggerCount >= 8) {
				reportData.setResult(RiskLevel.EARLY_ONSET.name());
			}
		} else {
			if ((reportData.getSex().equalsIgnoreCase("m") && triggerCount == 3)
					|| (reportData.getSex().equalsIgnoreCase("f") && triggerCount == 4)) {
				reportData.setResult(RiskLevel.IN_DANGER.name());
			} else if ((reportData.getSex().equalsIgnoreCase("m") && triggerCount >= 5)
					|| (reportData.getSex().equalsIgnoreCase("f") && triggerCount >= 7)) {
				reportData.setResult(RiskLevel.EARLY_ONSET.name());
			}
		}
	}

	/**
	 * Compte les déclencheurs uniques dans les notes du patient, indépendamment de
	 * la casse. Chaque déclencheur est compté une seule fois, même s'il apparaît
	 * plusieurs fois ou dans plusieurs notes.
	 * 
	 * @param notes Liste des notes du patient.
	 * @return Le nombre de déclencheurs uniques trouvés.
	 */
	private static int countTriggers(List<String> notes) {
		logger.info("count triggers process begins!");
		if (notes != null) {
			Set<String> foundTriggers = new HashSet<>();
			for (String note : notes) {
				String upperNote = note.toLowerCase(); // Convertir la note en majuscules
				upperNote = upperNote.replace(",", " ");
				for (String trigger : triggers) {
					if (upperNote.contains(trigger) && !foundTriggers.contains(trigger)) {
						foundTriggers.add(trigger);
						logger.info("trigger " + trigger + " found!");
					}
				}
			}
			return foundTriggers.size();
		} else {
			return 0;
		}
	}
}
