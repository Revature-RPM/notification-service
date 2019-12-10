package com.revature.rpm.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	byte[] secretBytes;
	
	public JWTService() {
		super();
		secretBytes = getSecretBytes();
	}
	
	/**
	 * Returns a byte[] of the JWT_SECRET Environment Variable. Be sure to share this with each developer who is building the project.
	 * @return
	 */
	private byte[] getSecretBytes() {
		try {
			Path path = Paths.get(System.getenv("JWT_SECRET"));
			System.out.println(Files.readAllBytes(path));
			return Files.readAllBytes(path);
		} catch (IOException e) {
			System.out.println("JWT Secret Read Error!");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns a SecretKey object, called by JWT creation/read methods
	 * @return
	 */
	private SecretKey getSecret() {
		System.out.println("JWT Get Secret Running");
		return Keys.hmacShaKeyFor(secretBytes);
	}	
	
	public Integer extractUserIdFromJWT(String jwsString) {
		try {
			System.out.println("extractUserIdFromJWT try block running");
			System.out.println("JWS String : " + jwsString);
			System.out.println(Jwts.parser().setSigningKey(getSecret()));
			
			Jws<Claims> jwsclaims = Jwts.parser()        
					.setSigningKey(getSecret())         
					.parseClaimsJws(jwsString);
			System.out.println("JWS<Claims> parsed");
			String userid = jwsclaims.getBody().get("sub", String.class);
			System.out.println("User ID : " + userid);
			return Integer.parseInt(userid);
		} catch (JwtException ex) {     
			System.out.println("JWT Authentication failure...");
			return null;
		}
	}
}
