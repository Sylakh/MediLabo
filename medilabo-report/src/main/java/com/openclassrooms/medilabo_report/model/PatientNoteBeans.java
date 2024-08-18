package com.openclassrooms.medilabo_report.model;

import java.util.List;

public class PatientNoteBeans {

	private String patientId;
	private String patientFirstName;
	private String patientLastName;
	private List<String> patientNotes;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public List<String> getPatientNotes() {
		return patientNotes;
	}

	public void setPatientNotes(List<String> patientNotes) {
		this.patientNotes = patientNotes;
	}

	public PatientNoteBeans(String patientId, String patientFirstName, String patientLastName,
			List<String> patientNotes) {
		super();
		this.patientId = patientId;
		this.patientFirstName = patientFirstName;
		this.patientLastName = patientLastName;
		this.patientNotes = patientNotes;
	}

	public PatientNoteBeans() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PatientNoteBeans [patientId=" + patientId + ", patientFirstName=" + patientFirstName
				+ ", patientLastName=" + patientLastName + ", patientNotes=" + patientNotes + "]";
	}

}
