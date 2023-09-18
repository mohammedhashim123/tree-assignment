package com.example.tree.assignment.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.InvalidAttributeValueException;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.tree.assignment.entity.Account;
import com.example.tree.assignment.entity.Statement;
import com.example.tree.assignment.model.SearchParam;
import com.example.tree.assignment.model.StatementResponseModel;
import com.example.tree.assignment.model.StatementSpecification;
import com.example.tree.assignment.repository.AccountRepository;
import com.example.tree.assignment.repository.StatementRepository;

@Service
public class StatementService {
	@Autowired
	StatementRepository statementRepository;
	@Autowired
	AccountRepository accountRepository;

	private static final Logger logger = LoggerFactory.getLogger(StatementService.class);

	public List<Statement> getAllStatement() {
		return statementRepository.findAll();
	}

	public StatementResponseModel getStatementByParam(SearchParam param) throws Exception {
		// Get the account info
		Optional<Account> account = accountRepository.findById(param.getAccountId());
		if (account.isEmpty()) {
			throw new ObjectNotFoundException(null, "account not found please enter valid account number");
		} else {
			param.setAccountNumber(account.get().getAccountNumber());
		}
		if (param.getFromAmount() != null && param.getToAmount() != null
				&& param.getFromAmount().compareTo(param.getToAmount()) == 1) {
			throw new InvalidAttributeValueException();
		}
		if (param.getFromDate() != null && param.getToDate() != null
				&& param.getFromDate().isAfter(param.getToDate())) {
			throw new InvalidAttributeValueException();
		}
		StatementSpecification<Statement> spesifications = new StatementSpecification<>(param);

		List<Statement> result = statementRepository.findAll(spesifications);

		return ApplyDateFiltering(param, result);
	}

	public StatementResponseModel ApplyDateFiltering(SearchParam param, List<Statement> result) throws ObjectNotFoundException, NoSuchAlgorithmException {
		LocalDate currentDate = LocalDate.now();
		// Set Default in case of null values for dates
		if (param.getFromDate() == null && param.getToDate() == null) {
			param.setFromDate(param.getFromDate() == null ? currentDate.minusDays(90L) : param.getFromDate());
			param.setToDate(param.getToDate() == null ? currentDate : param.getToDate());
		}
		logger.info("From Date {} and To date {} ", param.getFromDate(), param.getToDate());
		List<Statement> filteredList = result.stream().filter(
				f -> f.getDatefield().isAfter(param.getFromDate()) && f.getDatefield().isBefore(param.getToDate()))
				.collect(Collectors.toList());
		if (filteredList.size() == 0) {
			throw new ObjectNotFoundException(null,"Data not found");
		}
		StatementResponseModel response = new StatementResponseModel(Hash(param.getAccountNumber()), filteredList);
		return response;
	}

	public String Hash(String accountnumber) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		byte[] hash = messageDigest.digest(accountnumber.getBytes(StandardCharsets.UTF_8));

		// Convert the byte array to hexadecimal format
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
