package com.example.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.assignment.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

}
