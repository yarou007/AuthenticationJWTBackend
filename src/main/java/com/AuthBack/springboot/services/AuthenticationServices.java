package com.AuthBack.springboot.services;

import java.util.HashSet;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.AuthBack.springboot.models.ApplicationUser;
import com.AuthBack.springboot.models.LoginResponseDTO;
import com.AuthBack.springboot.models.Role;
import com.AuthBack.springboot.repository.RoleRepository;
import com.AuthBack.springboot.repository.UserRepository;

@Service
@Transactional
public class AuthenticationServices {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleReposiroty;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired 
	TokenService tokenService;
	
	public LoginResponseDTO loginUser(String username, String password) {
		try {
			Authentication auth = authManager.authenticate(
                   new UsernamePasswordAuthenticationToken(username, password)
					); 
			String token = tokenService.generateJwt(auth);
			return new LoginResponseDTO(userRepository.findByUsername(username).get(),token);
			
		}catch(AuthenticationException e) {
			return new LoginResponseDTO(null,"");
		}
		
	
	}
	
	public ApplicationUser RegisterUser(String username, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		Role userRole = roleReposiroty.findByAuthority("USER").get();
		
		Set<Role> authorities = new HashSet<>();
		
		authorities.add(userRole);
		
		ApplicationUser user = new ApplicationUser(0,username,encodedPassword,authorities); 
	   return userRepository.save(user);
	
	}
	
	
}
