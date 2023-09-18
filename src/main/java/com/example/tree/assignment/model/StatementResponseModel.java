package com.example.tree.assignment.model;

import java.util.List;

import com.example.tree.assignment.entity.Statement;

public class StatementResponseModel {
	private String accountNumber;
	private List<Statement> statementList;
	
	public StatementResponseModel(String accountNumber, List<Statement> statementList) {
		super();
		this.accountNumber = accountNumber;
		this.statementList = statementList;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public List<Statement> getStatementList() {
		return statementList;
	}
	public void setStatementList(List<Statement> statementList) {
		this.statementList = statementList;
	}
	
}
