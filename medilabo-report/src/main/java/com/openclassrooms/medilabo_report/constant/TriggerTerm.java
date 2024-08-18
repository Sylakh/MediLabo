package com.openclassrooms.medilabo_report.constant;

public enum TriggerTerm {
	HEMOGLOBINE_A1C("hémoglobine A1C"), MICROALBUMINE("microalbumine"), TAILLE("taille"), POIDS("poids"),
	FUMEUR("fumeur"), FUMEUSE("fumeuse"), ANORMAL("anormal"), CHOLESTEROL("cholestérol"), VERTIGES("vertiges"),
	RECHUTE("rechute"), REACTION("réaction"), ANTICORPS("anticorps");

	private final String description;

	TriggerTerm(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
