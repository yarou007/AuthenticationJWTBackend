package com.AuthBack.springboot.utils;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Component;

@Component
public class RsaKeyProprities {

	private RSAPublicKey publicKey; // lil encipher men chirti ena 
	private RSAPrivateKey privateKey; // lil decipher men chiret el user
	
	public RsaKeyProprities() {
		KeyPair Pair =  KeyGeneratedUtitlity.generateRsaKey();
		this.privateKey= (RSAPrivateKey) Pair.getPrivate();
		this.publicKey = (RSAPublicKey) Pair.getPublic();
	}

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
	}
 	
	
	
}
