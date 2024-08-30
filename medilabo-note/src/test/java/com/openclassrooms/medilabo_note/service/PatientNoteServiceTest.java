package com.openclassrooms.medilabo_note.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.medilabo_note.exception.PatientNoteNotFoundException;
import com.openclassrooms.medilabo_note.model.PatientNote;
import com.openclassrooms.medilabo_note.repository.PatientNoteRepository;

public class PatientNoteServiceTest {

	@Mock
	private PatientNoteRepository patientNoteRepository;

	@InjectMocks
	private PatientNoteService patientNoteService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindAll() {
		PatientNote note1 = new PatientNote();
		PatientNote note2 = new PatientNote();
		List<PatientNote> notes = Arrays.asList(note1, note2);

		when(patientNoteRepository.findAll()).thenReturn(notes);

		List<PatientNote> result = patientNoteService.findAll();

		assertEquals(2, result.size());
		verify(patientNoteRepository).findAll();
	}

	@Test
	public void testFindById_Success() throws Exception {
		PatientNote note = new PatientNote();
		when(patientNoteRepository.findByPatientId("1")).thenReturn(Optional.of(note));

		PatientNote result = patientNoteService.findById("1");

		assertEquals(note, result);
		verify(patientNoteRepository).findByPatientId("1");
	}

	@Test
	public void testFindById_NotFound() {
		when(patientNoteRepository.findByPatientId("1")).thenReturn(Optional.empty());

		Exception exception = assertThrows(PatientNoteNotFoundException.class, () -> {
			patientNoteService.findById("1");
		});

		assertEquals("PatientNote not found", exception.getMessage());
	}

	@Test
	public void testAddPatientNote() {
		PatientNote note = new PatientNote();
		when(patientNoteRepository.save(note)).thenReturn(note);

		PatientNote created = patientNoteService.add(note);

		assertEquals(note, created);
		verify(patientNoteRepository).save(note);
	}

	@Test
	public void testAddNote_Success() throws Exception {
		PatientNote note = new PatientNote();
		when(patientNoteRepository.findById("1")).thenReturn(Optional.of(note));

		PatientNote updated = patientNoteService.addNote("New Note", "1");

		verify(patientNoteRepository).findById("1");
		verify(patientNoteRepository).save(note);
	}

	@Test
	public void testAddNote_NotFound() {
		when(patientNoteRepository.findById("1")).thenReturn(Optional.empty());

		Exception exception = assertThrows(PatientNoteNotFoundException.class, () -> {
			patientNoteService.addNote("New Note", "1");
		});

		assertEquals("PatientNote not found", exception.getMessage());
	}
}
