package com.openclassrooms.medilabo_note.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.medilabo_note.model.PatientNote;

@Repository
public interface PatientNoteRepository extends MongoRepository<PatientNote, String> {

	Optional<PatientNote> findByPatientId(String id);

}
