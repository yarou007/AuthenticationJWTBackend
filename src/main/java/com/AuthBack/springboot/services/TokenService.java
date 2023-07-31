package com.AuthBack.springboot.services;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

	@Autowired
	private JwtEncoder jwtEncoder;
	
	@Autowired
	private JwtDecoder jwtDecoder;
	
	public String generateJwt(Authentication auth) {
		Instant now = Instant.now();
		String scope = auth.getAuthorities().stream() /* A stream is a sequence of elements that can be processed in a 
				functional style using Java Stream API operations.*/
				 /**
				The getAuthorities() method returns a collection of GrantedAuthority objects representing the authorities
				 (roles and permissions) of the authenticated user.
				*/
				.map(GrantedAuthority::getAuthority)
				/*
				 * returns the string representation of the authority (e.g., role name or permission name). By using the method reference GrantedAuthority::getAuthority,
				 *  we extract the string representation of the authority for each GrantedAuthority object.
				 */
				.collect(Collectors.joining(" "));
	          	/*
	         	 * collect() operation is used to combine these strings into a single string. 
	         	 * The Collectors.joining(" ") collector concatenates the authority strings using a space (" ") as the delimiter. 
	         	 */
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.subject(auth.getName())
				.claim("roles", scope)
				.build(); 
		
		
		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	
}
