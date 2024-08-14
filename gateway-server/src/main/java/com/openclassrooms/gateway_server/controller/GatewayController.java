package com.openclassrooms.gateway_server.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

	@GetMapping("/user")
	@ResponseBody
	public Principal index(Principal principal) {
		return principal;
	}

}
