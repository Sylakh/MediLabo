package com.openclassrooms.medilabo_frontend.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.medilabo_frontend.beans.PatientNoteBeans;
import com.openclassrooms.medilabo_frontend.proxies.MedilaboNoteProxy;

@Controller
public class PatientNoteController {

	private static final Logger logger = LogManager.getLogger("PatientNoteController");

	private final MedilaboNoteProxy medilaboNoteProxy;

	public PatientNoteController(MedilaboNoteProxy medilaboNoteProxy) {
		this.medilaboNoteProxy = medilaboNoteProxy;
	}

	@GetMapping("note/note/{id}")
	public String story(Model model, @PathVariable("id") String id) throws Exception {
		PatientNoteBeans patientNoteBeans = medilaboNoteProxy.getPatientNoteById(id);
		model.addAttribute("patientNote", patientNoteBeans);
		return "note/note";
	}

	@PostMapping("note/add/{id}")
	/*
	 * public String addPatientNote(@PathVariable String
	 * id, @RequestParam("noteContent") String noteContent, RedirectAttributes
	 * redirectAttributes) { try { logger.info("add note process begins for id " +
	 * id + " with note: " + noteContent);
	 * medilaboNoteProxy.addNoteToPatientId(noteContent, id);
	 * redirectAttributes.addFlashAttribute("successMessage",
	 * "Note ajoutée avec succès!"); } catch (Exception e) {
	 * redirectAttributes.addFlashAttribute("errorMessage",
	 * "Erreur lors de l'ajout de la note."); } return "redirect:/note/note/" + id;
	 * }
	 */
	public String addPatientNote(@PathVariable String id, @RequestParam("noteContent") String noteContent, Model model)
			throws Exception {
		int patientId = Integer.parseInt(id);
		logger.info("add note process begins for id " + patientId + " with note: " + noteContent);
		medilaboNoteProxy.addNoteToPatientId(noteContent, id);
		PatientNoteBeans patientNoteBeans = medilaboNoteProxy.getPatientNoteById(id);
		model.addAttribute("patientNote", patientNoteBeans);
		return "note/note";
	}
}
