package com.openclassrooms.microback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.microback.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
