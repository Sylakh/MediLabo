package com.openclassrooms.medilabo_frontend.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openclassrooms.medilabo_frontend.beans.PatientBeans;
import com.openclassrooms.medilabo_frontend.beans.PatientNoteBeans;
import com.openclassrooms.medilabo_frontend.proxies.MedilaboNoteProxy;
import com.openclassrooms.medilabo_frontend.proxies.MicroBackProxy;

import jakarta.validation.Valid;

@Controller
public class PatientController {

	private static final Logger logger = LogManager.getLogger("PatientController");

	private final MicroBackProxy patientProxy;

	public PatientController(MicroBackProxy patientProxy) {
		this.patientProxy = patientProxy;
	}

	@Autowired
	private MedilaboNoteProxy medilaboNoteProxy;

	@GetMapping("/")
	public String index(Model model) {

		return "index";
	}

	@GetMapping("/logout")
	public String logout() {

		return "index";
	}

	@GetMapping("/home")
	public String home(Model model) {
		List<PatientBeans> patients = patientProxy.getAllPatient();
		model.addAttribute("patients", patients);
		return "home";
	}

	@GetMapping("/patient/add")
	public String addPatientForm(Model model) {
		logger.info("Get request for add page");
		model.addAttribute("patientBeans", new PatientBeans());
		return "patient/add";
	}

	@PostMapping("/patient/validate")
	public String validate(@ModelAttribute("patientBeans") PatientBeans patient, BindingResult result, Model model) {
		logger.info("Create a new patient in database");
		if (result.hasErrors()) {
			return "patient/add";
		}
		ResponseEntity<PatientBeans> responseEntity = patientProxy.add(patient);
		PatientNoteBeans patientNote = new PatientNoteBeans();
		String patientId = Integer.toString(responseEntity.getBody().getId());
		patientNote.setPatientId(patientId);
		patientNote.setPatientFirstName(responseEntity.getBody().getFirstName());
		patientNote.setPatientLastName(responseEntity.getBody().getLastName());
		medilaboNoteProxy.addPatient(patientNote);

		model.addAttribute("patients", patientProxy.getAllPatient());
		return "redirect:/";

	}

	@GetMapping("/patient/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		logger.info("Delete patient id: " + id);
		patientProxy.delete(id);
		model.addAttribute("patients", patientProxy.getAllPatient());
		return "redirect:/";
	}

	@GetMapping("/patient/update/{id}")
	public String updatePatientForm(@PathVariable("id") Integer id, Model model) {
		logger.info("Get request for update page");
		PatientBeans patient = patientProxy.findPatientById(id);
		model.addAttribute("patient", patient);
		return "patient/update";
	}

	@PostMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable("id") Integer id, @Valid PatientBeans patient, BindingResult result,
			Model model) throws Exception {
		logger.info("update patient id: " + id);
		if (result.hasErrors()) {
			return "patient/update/" + id;
		}
		patientProxy.update(patient, id);
		model.addAttribute("patients", patientProxy.getAllPatient());
		return "redirect:/";
	}

	@GetMapping("/auth")
	@ResponseBody
	public Authentication getUserAuthenticated(Authentication authentication) {
		logger.info("Get user authenticated");

		return authentication;
	}

}
