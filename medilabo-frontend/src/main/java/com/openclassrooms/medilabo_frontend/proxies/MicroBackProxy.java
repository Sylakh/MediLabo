package com.openclassrooms.medilabo_frontend.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.medilabo_frontend.beans.PatientBeans;

import jakarta.validation.Valid;

@FeignClient(name = "microback", url = "localhost:8080/microback")
public interface MicroBackProxy {

	@GetMapping("/patient")
	List<PatientBeans> getAllPatient();

	@GetMapping("/patient/{id}")
	PatientBeans findPatientById(@PathVariable("id") int id);

	@PostMapping("/patient")
	ResponseEntity<PatientBeans> add(@Valid @RequestBody PatientBeans patient);

	@DeleteMapping("/patient/{id}")
	void delete(@PathVariable("id") int id);

	@PostMapping("/patient/{id}")
	void update(@Valid @RequestBody PatientBeans patient, @PathVariable("id") Integer id);

}
