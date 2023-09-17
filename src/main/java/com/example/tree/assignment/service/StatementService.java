package com.example.tree.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tree.assignment.entity.Statement;
import com.example.tree.assignment.repository.StatementRepository;

@Service
public class StatementService {
	@Autowired
	StatementRepository statementRepository;
	
	public List<Statement> getAllStatement(){
		return statementRepository.findAll();
	}
}
