package com.openclassrooms.medilabo_report.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.medilabo_report.DTO.ReportDataDTO;
import com.openclassrooms.medilabo_report.model.PatientBeans;
import com.openclassrooms.medilabo_report.model.PatientNoteBeans;
import com.openclassrooms.medilabo_report.model.ReportData;
import com.openclassrooms.medilabo_report.service.MicrobackService;
import com.openclassrooms.medilabo_report.service.NoteService;
import com.openclassrooms.medilabo_report.service.PatientDataAnalyzer;
import com.openclassrooms.medilabo_report.service.ReportDataService;

@RestController
public class ReportController {

	@Autowired
	private MicrobackService microbackService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private ReportDataService reportDataService;

	@GetMapping("/hellox2")
	public String hellox2() {
		return microbackService.helloFromMicroback() + " " + noteService.helloFromNote();
	}

	@GetMapping("/report")
	public List<ReportDataDTO> report() throws Exception {
		List<PatientBeans> patients = new ArrayList<>();
		List<PatientNoteBeans> notes = new ArrayList<>();
		patients = microbackService.getAllPatients();
		notes = noteService.getAllNotes();
		List<ReportData> reports = new ArrayList<>();
		reports = reportDataService.generateReports(patients, notes);

		for (ReportData report : reports) {
			PatientDataAnalyzer.analyzePatientData(report);
		}

		List<ReportDataDTO> listReportDTO = new ArrayList<>();
		for (ReportData report : reports) {
			listReportDTO.add(new ReportDataDTO(report.getId(), report.getResult()));
			System.out.println(report.toString());
		}

		return listReportDTO;
	}

}
