package com.example.coffeemoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coffeemoney.model.entity.BudgetEntity;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Integer> {

}
