package com.openclassrooms.medilabo_report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.medilabo_report.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;

	@GetMapping("/note/hello")
	public String hello() {
		return noteService.helloFromNote();
	}
}
