package com.AuthBack.springboot.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.KeyGenerator;

public class KeyGeneratedUtitlity {

	public static KeyPair generateRsaKey() {
		
		KeyPair keyPair;
		
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA"); 
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair(); 
		}catch(Exception e) {
			
			throw new IllegalStateException();
		}
		
		return keyPair;
	}
}
