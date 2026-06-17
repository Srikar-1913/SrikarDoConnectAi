package com.wipro.doconnect.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

	private final String SECRET = "mysecretkeymysecretkeymysecretkey";

	private final Key key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET.getBytes());

	public String generateToken(String email, String role) {
		return Jwts.builder().setSubject(email).claim("role", role).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).signWith(key).compact();
	}

	public String extractEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	public String extractRole(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role",
				String.class);
	}
}