package com.example.tree.assignment.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.example.tree.assignment.entity.Statement;
import com.example.tree.assignment.helpers.ConvertDate;

public class StatementSpecification<T extends Statement> implements Specification<T>  {
	public StatementSpecification(SearchParam searchParam) {
		super();
		this.setSearchParam(searchParam);
	}
	private SearchParam searchParam;
	
	public SearchParam getSearchParam() {
		return searchParam;
	}
	public void setSearchParam(SearchParam searchParam) {
		this.searchParam = searchParam;
	}
	
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(criteriaBuilder.equal(root.get("accountId"), this.searchParam.getAccountId()));
		
		if(this.searchParam.getFromAmount() !=null &&  this.searchParam.getToAmount() !=null) {
			predicates.add(criteriaBuilder.between(root.get("amount"), this.searchParam.getFromAmount(), this.searchParam.getToAmount()));
		}
		
		Predicate[] p = new Predicate[predicates.size()];
		return criteriaBuilder.and(predicates.toArray(p));
	}
	
}
