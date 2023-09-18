package com.example.tree.assignment.jwtconfig;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Set the response content type to JSON
        response.setContentType("application/json");
        
        // Write the custom error message to the response body
        String errorMessage = "Unauthorized Request";
        response.getWriter().write("{\"error\":\"" + errorMessage + "\"}");
        
        // Set the HTTP status code to 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}