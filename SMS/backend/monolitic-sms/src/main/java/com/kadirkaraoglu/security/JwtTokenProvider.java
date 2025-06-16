package com.kadirkaraoglu.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	@Value("${jwt.key}")
	private String SECRET;
	

	public boolean validateToken(String token,UserDetails userDetails) {
		String username = extractUserName(token);
		Date exprirationDate = extractExpiration(token);
		return userDetails.getUsername().equals(username) && !exprirationDate.before(new Date());
		
	}
	private Date extractExpiration(String token) {
		
		Claims  claims = Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getExpiration();
				
	}
	   public String generateRefreshToken(String username) {
	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 60 * 1000)) 
	                .signWith(getSignKey(), SignatureAlgorithm.HS256) 
	                .compact();
	    }
	   
	public String extractUserName(String token) {
		
				Claims  claims = Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
		
			
	}
	
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<String, Object>();

		
		return  Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 *30 ))
				.signWith(getSignKey(), SignatureAlgorithm.HS256 )
				.compact();
		
	}
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return  Keys.hmacShaKeyFor(keyBytes);
	}
}


