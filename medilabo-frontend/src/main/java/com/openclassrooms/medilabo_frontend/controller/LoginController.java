package com.openclassrooms.medilabo_frontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.openclassrooms.medilabo_frontend.beans.PatientBeans;
import com.openclassrooms.medilabo_frontend.model.UserData;
import com.openclassrooms.medilabo_frontend.proxies.MicroBackProxy;
import com.openclassrooms.medilabo_frontend.service.KeycloakTokenService;

@Controller
@SessionAttributes("userData")
public class LoginController {

	@Autowired
	public KeycloakTokenService keycloakTokenService;

	private final MicroBackProxy patientProxy;

	public LoginController(MicroBackProxy patientProxy) {
		this.patientProxy = patientProxy;
	}

	// Initialiser le modèle avec un objet UserData
	@GetMapping("/")
	public String showLoginForm(Model model) {
		model.addAttribute("userData", new UserData(null, null));
		return "login";
	}

	// Gérer la soumission du formulaire
	@PostMapping("/login")
	public String processLogin(UserData userData, Model model) {
		model.addAttribute("userData", userData);
		keycloakTokenService.setUserData(userData);
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String home(Model model) {
		UserData userData = (UserData) model.getAttribute("userData");
		if (keycloakTokenService.isUserDataValid(userData)) {
			model.addAttribute("message", "Welcome " + userData.getUsername() + "!");
			List<PatientBeans> patients = patientProxy.getAllPatient();
			model.addAttribute("patients", patients);
		} else {
			model.addAttribute("message", "No user data found!");
		}

		return "home";
	}

	// Méthode pour terminer la session si nécessaire
	@GetMapping("/logout")
	public String logout(SessionStatus sessionStatus, Model model) {
		model.addAttribute("userData", new UserData(null, null));

		sessionStatus.setComplete();
		keycloakTokenService.logout();
		return "redirect:/";
	}
}
