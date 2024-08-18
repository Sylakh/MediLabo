package com.openclassrooms.medilabo_report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.medilabo_report.service.MicrobackService;

@RestController
public class MicrobackController {

	@Autowired
	private MicrobackService microbackService;

	@GetMapping("/microback/hello")
	public String hello() {
		return microbackService.helloFromMicroback();
	}

}
