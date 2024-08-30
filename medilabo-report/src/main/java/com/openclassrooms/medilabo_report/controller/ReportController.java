package com.openclassrooms.medilabo_report.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger("PatientReportController");

	@Autowired
	private MicrobackService microbackService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private ReportDataService reportDataService;

	@GetMapping("/report")
	public List<ReportDataDTO> report() throws Exception {
		logger.info("report process begins!");
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
		}
		logger.info("report process done!");
		return listReportDTO;
	}

}
