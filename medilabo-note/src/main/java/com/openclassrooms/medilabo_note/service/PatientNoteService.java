package com.openclassrooms.medilabo_note.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.openclassrooms.medilabo_note.exception.PatientNoteNotFoundException;
import com.openclassrooms.medilabo_note.model.PatientNote;
import com.openclassrooms.medilabo_note.repository.PatientNoteRepository;

@Service
public class PatientNoteService {

	private static final Logger logger = LogManager.getLogger("PatientNoteService");

	@Autowired
	private PatientNoteRepository patientNoteRepository;

	@GetMapping("/hello")
	public String hello() {
		logger.info("test hello");
		return "Hello from medilabo-note!";
	}

	public List<PatientNote> findAll() {
		logger.info("Get all PatientNote");
		return patientNoteRepository.findAll();
	}

	public PatientNote findById(String id) throws Exception {
		logger.info("Get PatientNote #," + id);
		Optional<PatientNote> optionalPatientNote = patientNoteRepository.findByPatientId(id);
		if (optionalPatientNote.isPresent()) {
			return optionalPatientNote.get();
		} else {
			throw new PatientNoteNotFoundException("PatientNote not found");
		}
	}

	public PatientNote add(PatientNote patientNote) {
		logger.info("Create a new patient");
		return patientNoteRepository.save(patientNote);
	}

	public PatientNote addNote(String note, String id) throws Exception {
		logger.info("Update patientNote id: " + id);
		Optional<PatientNote> optionalPatientNote = patientNoteRepository.findById(id);
		if (optionalPatientNote.isPresent()) {
			PatientNote patientNoteFound = optionalPatientNote.get();
			List<String> notes = new ArrayList<>();
			notes = patientNoteFound.getPatientNotes();
			if (notes == null) {
				notes = new ArrayList<>(Collections.singletonList(note));
			} else {
				notes.add(note);
			}
			patientNoteFound.setPatientNotes(notes);
			return patientNoteRepository.save(patientNoteFound);
		} else {
			throw new PatientNoteNotFoundException("PatientNote not found");
		}
	}

}
