package com.openclassrooms.medilabo_frontend.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.openclassrooms.medilabo_frontend.DTO.PatientDTO;
import com.openclassrooms.medilabo_frontend.beans.PatientBeans;
import com.openclassrooms.medilabo_frontend.beans.ReportDataDTOBeans;
import com.openclassrooms.medilabo_frontend.model.UserData;
import com.openclassrooms.medilabo_frontend.proxies.MedilaboReportProxy;
import com.openclassrooms.medilabo_frontend.proxies.MicroBackProxy;
import com.openclassrooms.medilabo_frontend.service.KeycloakTokenService;

@Controller
@SessionAttributes("userData")
public class LoginController {

	private static final Logger logger = LogManager.getLogger("LoginController");

	@Autowired
	public KeycloakTokenService keycloakTokenService;

	private final MicroBackProxy patientProxy;
	private final MedilaboReportProxy medilaboReportProxy;

	public LoginController(MicroBackProxy patientProxy, MedilaboReportProxy medilaboReportProxy) {
		this.patientProxy = patientProxy;
		this.medilaboReportProxy = medilaboReportProxy;
	}

	// Initialiser le modèle avec un objet UserData
	@GetMapping("/")
	public String showLoginForm(Model model) {
		logger.info("Get request / ");
		model.addAttribute("userData", new UserData(null, null));
		return "login";
	}

	// Gérer la soumission du formulaire
	@PostMapping("/login")
	public String processLogin(UserData userData, Model model) {
		logger.info("Post request /login ");
		model.addAttribute("userData", userData);
		keycloakTokenService.setUserData(userData);
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String home(Model model) {
		logger.info("Get request /home ");
		UserData userData = (UserData) model.getAttribute("userData");
		if (keycloakTokenService.isUserDataValid(userData)) {
			model.addAttribute("message", "Welcome " + userData.getUsername() + "!");
			List<PatientBeans> listPatient = patientProxy.getAllPatient();
			List<ReportDataDTOBeans> reports = medilaboReportProxy.report();
			List<PatientDTO> patients = new ArrayList<>();
			for (PatientBeans patientBeans : listPatient) {
				patients.add(
						new PatientDTO(patientBeans.getId(), patientBeans.getLastName(), patientBeans.getFirstName(),
								patientBeans.getBirthday(), patientBeans.getGender(), patientBeans.getAddress(),
								patientBeans.getPhone(), getResultById(reports, (long) patientBeans.getId())));
			}
			model.addAttribute("patients", patients);
		} else {
			model.addAttribute("message", "No user data found!");
		}

		return "home";
	}

	// Méthode pour terminer la session si nécessaire
	@GetMapping("/logout")
	public String logout(SessionStatus sessionStatus, Model model) {
		logger.info("Get request /logout ");
		model.addAttribute("userData", new UserData(null, null));
		sessionStatus.setComplete();
		keycloakTokenService.logout();
		return "redirect:/";
	}

	public String getResultById(List<ReportDataDTOBeans> reports, Long i) {
		for (ReportDataDTOBeans report : reports) {
			if (report.getId().equals(i)) {
				return report.getResult();
			}
		}
		return null;
	}
}
