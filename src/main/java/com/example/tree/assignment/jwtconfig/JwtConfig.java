package com.example.tree.assignment.jwtconfig;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtConfig implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final long JWT_TOKEN_VALIDITY = 300000;

	@Value("${jwt.secret}")
	private String secret;

//retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	public String decodeTokenAndGetSub(String token) throws IOException {
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String[] chunks = token.split("\\.");
		String payload = new String(decoder.decode(chunks[1]));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(payload).get("preferred_username").asText();
	}

//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

//generate token for user
	public String generateToken(String userName ,String role) {
		Map<String, Object> claims = new HashMap<>();
		List<SimpleGrantedAuthority> rols = new ArrayList<>();
		rols.add(new SimpleGrantedAuthority(role));
		claims.put("role", rols);
		return doGenerateToken(claims, userName);
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
//while creating the token -
//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//2. Sign the JWT using the HS512 algorithm and secret key.
//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
//   compaction of the JWT to a URL-safe string 
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setHeaderParam("type", "access_token").setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
}
