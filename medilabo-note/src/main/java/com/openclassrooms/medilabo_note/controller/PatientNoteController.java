package com.openclassrooms.medilabo_note.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.openclassrooms.medilabo_note.model.PatientNote;
import com.openclassrooms.medilabo_note.service.PatientNoteService;

@RestController
public class PatientNoteController {

	private static final Logger logger = LogManager.getLogger("PatientNoteController");

	@Autowired
	private PatientNoteService patientNoteService;

	@GetMapping("/hello")
	public String hello() {
		return "Hello from Medilabo-note!";
	}

	@GetMapping("/patientnote")
	public List<PatientNote> getAllPatientNote() {
		logger.info("Get all PatientNote");
		return patientNoteService.findAll();
	}

	@GetMapping("patientnote/{id}")
	public PatientNote getPatientNoteById(@PathVariable("id") String id) throws Exception {
		logger.info("Get PatientNote #," + id);
		return patientNoteService.findById(id);
	}

	@PostMapping("patientnote")
	public ResponseEntity<PatientNote> addPatient(@RequestBody PatientNote patientNote) {
		logger.info("Create a new patientNote");
		PatientNote patientNoteSaved = patientNoteService.add(patientNote);
		if (Objects.isNull(patientNoteSaved)) {
			return ResponseEntity.noContent().build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(patientNoteSaved.getPatientId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/patientnote/{id}")
	public PatientNote addNoteToPatientId(@RequestBody String note, @PathVariable("id") String id) throws Exception {
		logger.info("Update patientNote id: " + id);
		return patientNoteService.addNote(note, id);
	}
}
