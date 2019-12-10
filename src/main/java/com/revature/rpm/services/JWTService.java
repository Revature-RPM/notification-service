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
/**
 * 
 * @author James Meadows
 * @author Stefano Georges
 * @author Chong Ting
 * @author Christopher Troll
 * @author Emad Davis
 *
 */
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
			Jws<Claims> jwsclaims = Jwts.parser()        
					.setSigningKey(getSecret())         
					.parseClaimsJws(jwsString);
			String userid = jwsclaims.getBody().get("sub", String.class);
			return Integer.parseInt(userid);
		} catch (JwtException ex) {     
			System.out.println("JWT Authentication failure...");
			return null;
		}
	}
}
