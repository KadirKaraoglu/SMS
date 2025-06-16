package com.kadirkaraoglu.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kadirkaraoglu.service.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsServiceImpl detailsServiceImpl;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,UserDetailsServiceImpl detailsServiceImpl) {
		this .detailsServiceImpl =detailsServiceImpl; 
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
       
			
		try {
			String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
             token = authHeader.substring(7);
             userName = jwtTokenProvider.extractUserName(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = detailsServiceImpl.loadUserByUsername(userName);
          
            if (jwtTokenProvider.validateToken(token, user)) {
           
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
          
        }
		} catch (ExpiredJwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("{\r\n"
					+ "    \"states\": 401\r\n"
					+ "    \"exception\": {\r\n"
					+ "        \"hostName\": \"kadir\",\r\n"
					+ "        \"path\": \"/api/auth/login-student\",\r\n"
					+ "        \"createTime\": \"2025-05-18T08:56:00.532+00:00\",\r\n"
					+ "        \"message\": \"Your session has timed out\"\r\n"
					+ "    }\r\n"
					+ "}");
			
			return;
		} 
		
        
        filterChain.doFilter(request, response);
	
	}
}