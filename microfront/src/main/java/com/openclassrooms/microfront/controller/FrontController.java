package com.openclassrooms.microfront.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.microfront.beans.PatientBeans;
import com.openclassrooms.microfront.proxies.MicroBackProxy;

import jakarta.validation.Valid;

@Controller
public class FrontController {

	private static final Logger logger = LogManager.getLogger("FrontController");

	private final MicroBackProxy patientProxy;

	public FrontController(MicroBackProxy patientProxy) {
		this.patientProxy = patientProxy;
	}

	@GetMapping("/")
	public String accueil(Model model) {
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
	public String validate(@Validated @ModelAttribute("patientBeans") PatientBeans patient, BindingResult result,
			Model model) {
		logger.info("Create a new patient in database");
		if (result.hasErrors()) {
			return "patient/add";
		}
		patientProxy.add(patient);
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
			return "patient/update";
		}
		patientProxy.update(patient, id);
		model.addAttribute("patients", patientProxy.getAllPatient());
		return "redirect:/";
	}

}
