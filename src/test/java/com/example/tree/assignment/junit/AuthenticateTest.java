package com.example.tree.assignment.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.tree.assignment.controller.AuthenticationController;
import com.example.tree.assignment.model.AuthRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AuthenticateTest {
	@Autowired
	AuthenticationController authenticationController;
	@Test
	public void testSuccessAuthenticate() throws Exception {
		AuthRequest request = new AuthRequest();
		
		request.setUsername("admin");
		request.setPassword("admin");
		
		ResponseEntity<?> response = authenticationController.createAuthenticationToken(request);
		assertEquals(200, response.getStatusCodeValue());
	}
	@Test
	public void testFailureAuthenticate() throws Exception {
		AuthRequest request = new AuthRequest();
		
		request.setUsername("x");
		request.setPassword("y");
		
		ResponseEntity<?> response = authenticationController.createAuthenticationToken(request);
		assertEquals(400, response.getStatusCodeValue());
	}
}
