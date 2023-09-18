package com.example.tree.assignment.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.example.tree.assignment.entity.Statement;
import com.example.tree.assignment.model.StatementSpecification;

public class StatementRepoImp extends SimpleJpaRepository<Statement, Integer> implements StatementRepository {
	
	EntityManager entityManager;
	
	public StatementRepoImp(EntityManager entityManager) {
		super(Statement.class, entityManager);
		this.entityManager = entityManager;
	}
	public List<Long> findByAccountId(StatementSpecification<Statement> spesifications) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);

		Root<Statement> root = query.from(getDomainClass());
		query.where(spesifications.toPredicate(root, query, criteriaBuilder));
		query.multiselect(root.get("account_id"));

		TypedQuery<Long> typedQuery = entityManager.createQuery(query);
		return typedQuery.getResultList();
	}

}
