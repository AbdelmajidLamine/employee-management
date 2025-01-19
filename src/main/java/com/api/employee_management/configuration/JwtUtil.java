package com.api.employee_management.configuration;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static final long EXPIRATION_TIME = 86400000;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// 2. Validate Token
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(String username, List<String> roles) {
		return Jwts.builder()
				.setSubject(username)
				.claim("roles", roles)  // Add roles to the token
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SECRET_KEY)
				.compact();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// 5. Extract Expiration Date
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
