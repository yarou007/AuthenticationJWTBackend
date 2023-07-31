package com.AuthBack.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AuthBack.springboot.models.ApplicationUser;
import com.AuthBack.springboot.models.LoginResponseDTO;
import com.AuthBack.springboot.models.RegistrationDTO;
import com.AuthBack.springboot.services.AuthenticationServices;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AthenticationController {

	@Autowired
	private AuthenticationServices authenticationServices;
	
	@PostMapping("/register")
	public ApplicationUser registerUser(@RequestBody RegistrationDTO body) {
		
		return authenticationServices.RegisterUser(body.getUsername(),body.getPassword());
	}
	
	@PostMapping("/login")
	public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
		
		return authenticationServices.loginUser(body.getUsername(), body.getPassword());
	}
}
