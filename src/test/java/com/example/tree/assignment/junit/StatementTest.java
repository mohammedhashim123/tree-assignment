package com.example.tree.assignment.junit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.tree.assignment.controller.AccountController;
import com.example.tree.assignment.controller.StatementController;
import com.example.tree.assignment.entity.User;
import com.example.tree.assignment.jwtconfig.JwtConfig;
import com.example.tree.assignment.model.SearchParam;
import com.example.tree.assignment.model.StatementResponseModel;
import com.example.tree.assignment.service.StatementService;

import io.jsonwebtoken.lang.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class StatementTest {
	@Autowired
	StatementService statementService;
	@Autowired
	StatementController statementController;
	@Autowired
	JwtConfig jwtConfig;
	@Autowired
	AccountController accountController;

	@Test
	public void testSearchByAccountIdOnly() throws Exception {
		SearchParam param = new SearchParam();
		param.setAccountId(3);

		StatementResponseModel response = statementService.getStatementByParam(param);
		assertTrue(response.getStatementList().size() > 0);

	}

	@Test
	public void testSearchByAccountIdOnlyWithNoData() throws Exception {
		User user = new User();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				user, null,
				Collections.arrayToList(new SimpleGrantedAuthority[] { new SimpleGrantedAuthority("admin") }));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		SearchParam param = new SearchParam();
		param.setAccountId(1);

		ResponseEntity<?> response = statementController.getStatementByAccount(param);
		assertEquals(404, response.getStatusCodeValue());

	}

	@Test
	public void testSearchByAccountIdAndDate() throws Exception {
		User user = new User();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				user, null,
				Collections.arrayToList(new SimpleGrantedAuthority[] { new SimpleGrantedAuthority("admin") }));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		String date = "2016-08-16";

		SearchParam param = new SearchParam();
		param.setAccountId(1);
		param.setFromDate(LocalDate.parse(date));
		param.setToDate(LocalDate.now());

		ResponseEntity<?> response = statementController.getStatementByAccount(param);
		assertEquals(200, response.getStatusCodeValue());

	}

	@Test
	public void testSearchByAccountIdAndAmount() throws Exception {
		User user = new User();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				user, null,
				Collections.arrayToList(new SimpleGrantedAuthority[] { new SimpleGrantedAuthority("admin") }));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		SearchParam param = new SearchParam();
		param.setAccountId(3);
		param.setFromAmount(BigDecimal.valueOf(1));
		param.setToAmount(BigDecimal.valueOf(2000));

		ResponseEntity<?> response = statementController.getStatementByAccount(param);
		assertEquals(200, response.getStatusCodeValue());

	}

	@Test
	public void testAccountList() throws Exception {
		User user = new User();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				user, null,
				Collections.arrayToList(new SimpleGrantedAuthority[] { new SimpleGrantedAuthority("admin") }));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		ResponseEntity<?> response = accountController.getAccountsList();
		assertEquals(200, response.getStatusCodeValue());

	}

}
