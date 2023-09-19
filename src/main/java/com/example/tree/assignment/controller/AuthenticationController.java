package com.example.tree.assignment.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tree.assignment.entity.User;
import com.example.tree.assignment.jwtconfig.JwtConfig;
import com.example.tree.assignment.model.AuthRequest;
import com.example.tree.assignment.model.AuthResponse;
import com.example.tree.assignment.service.UserService;

@RestController
public class AuthenticationController {
	@Autowired
	JwtConfig jwtConfig;

	@Autowired
	UserService userService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest)
			throws Exception {

		final UserDetails user = userService.loadUserByUsername(authenticationRequest.getUsername());
		if(user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not found");
		}
		List<String> roles = user.getAuthorities().stream().map(item ->
		  item.getAuthority()) .collect(Collectors.toList());
		
		final String token = jwtConfig.generateToken(user.getUsername(), roles.get(0));

		
		return ResponseEntity.ok(new AuthResponse(token, roles.get(0)));
	}
}
