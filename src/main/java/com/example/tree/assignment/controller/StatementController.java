package com.example.tree.assignment.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tree.assignment.model.SearchParam;
import com.example.tree.assignment.service.StatementService;

@RestController
@RequestMapping("/statement")
public class StatementController {

	@Autowired
	StatementService statementService;
	
	private static final Logger logger = LoggerFactory.getLogger(StatementController.class);
	
	@GetMapping("/statementList")
	public ResponseEntity<?> getAccountsList() {
		return ResponseEntity.ok(statementService.getAllStatement());
	}
	@GetMapping("/getStatementByAccount")
	public ResponseEntity<?> getStatementByAccount(@Valid SearchParam searchParam) {
		
		logger.info("Search by Account Id  " +searchParam.getAccountId());
		
		return ResponseEntity.ok(statementService.getStatementByParam(searchParam));
	}
}
