package com.openclassrooms.medilabo_frontend.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.medilabo_frontend.beans.PatientNoteBeans;

@FeignClient(name = "medilabo-note", url = "localhost:8080/medilabo-note")
public interface MedilaboNoteProxy {

	@GetMapping("patientnote/{id}")
	PatientNoteBeans getPatientNoteById(@PathVariable("id") String id) throws Exception;

	@PostMapping("/patientnote/{id}")
	PatientNoteBeans addNoteToPatientId(@RequestBody String note, @PathVariable("id") String id) throws Exception;

	@PostMapping("patientnote")
	public ResponseEntity<PatientNoteBeans> addPatient(@RequestBody PatientNoteBeans patientNote);

}
