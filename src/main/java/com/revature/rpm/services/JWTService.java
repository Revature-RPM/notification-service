package com.revature.rpm.services;

import javax.crypto.SecretKey;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.revature.rpm.components.Listener;

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
public class JWTService implements InitializingBean{
	byte[] secretBytes;
	
	@Value("${JWT_SECRET:jwtsecret-reallylongsecretrequiredjwtsecret-reallylongsecretrequired}")
	String secret;
	
	Logger logger = Logger.getLogger(Listener.class);
	public JWTService() {
		super();
	}
	
	// afterPropertiesSet is the appropriate place to initialize them
	@Override
	public void afterPropertiesSet() throws Exception {
		secretBytes = getSecretBytes();
	}
	
	/**
	 * Returns a byte[] of the JWT_SECRET Environment Variable. Be sure to share this with each developer who is building the project.
	 * @return
	 */
	private byte[] getSecretBytes() {
		return secret.getBytes();
	}
	
	/**
	 * Returns a SecretKey object, called by JWT creation/read methods
	 * @return
	 */
	private SecretKey getSecret() {
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
			logger.info("JWT Authentication failure...");
			return null;
		}
	}
}
