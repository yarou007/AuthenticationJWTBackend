package com.AuthBack.springboot.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.AuthBack.springboot.models.ApplicationUser;
import com.AuthBack.springboot.models.Role;
import com.AuthBack.springboot.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("Inside user details ");
     return userRepository.findByUsername(username).orElseThrow( ()-> new UsernameNotFoundException("Username not found"));	
     /*if (!username.equals("Ethan")) throw new UsernameNotFoundException("No user called Ethan");
     
     Set<Role> roles = new HashSet<>();
     roles.add(new Role(1,"USER"));
     
     return new ApplicationUser(1,"Ethan",passEncoder.encode("pass"),roles);*/
  
	}

}
