package com.example.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.assignment.entity.Statement;
import com.example.assignment.repository.StatementRepository;

@Service
public class StatementService {
	@Autowired
	StatementRepository statementRepository;
	
	public List<Statement> getAllStatement(){
		return statementRepository.findAll();
	}
}
