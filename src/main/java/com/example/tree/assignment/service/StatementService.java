package com.example.tree.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.tree.assignment.entity.Statement;
import com.example.tree.assignment.model.SearchParam;
import com.example.tree.assignment.model.StatementSpecification;
import com.example.tree.assignment.repository.StatementRepository;

@Service
public class StatementService {
	@Autowired
	StatementRepository statementRepository;
	
	public List<Statement> getAllStatement(){
		return statementRepository.findAll();
	}
	public Page<Statement> getStatementByParam(SearchParam param){
		StatementSpecification<Statement> spesifications = new StatementSpecification<>(param);
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Direction.DESC, "accountId"));
		
		Page<Statement> result = statementRepository.findAll(spesifications,pageable);
		
		return result;
	}
}
