package com.example.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.assignment.entity.Statement;

@Repository
public interface StatementRepository extends JpaRepository<Statement,Integer> {

}
