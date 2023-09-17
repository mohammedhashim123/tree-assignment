package com.example.tree.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tree.assignment.service.StatementService;

@RestController
@RequestMapping("/statement")
public class StatementController {

	@Autowired
	StatementService statementService;

	@GetMapping("/statementList")
	public ResponseEntity<?> getAccountsList() {
		return ResponseEntity.ok(statementService.getAllStatement());
	}
}
