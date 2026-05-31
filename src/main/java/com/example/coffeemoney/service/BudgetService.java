package com.example.coffeemoney.service;

import org.springframework.stereotype.Service;

import com.example.coffeemoney.repository.BudgetRepository;

@Service
public class BudgetService {
	private final BudgetRepository budgetRepository;

	public BudgetService(BudgetRepository budgetRepository) {
		this.budgetRepository = budgetRepository;
	}

	public Integer getBudget() {
		return budgetRepository.findById(1)
				.orElseThrow(() -> new IllegalArgumentException("Budget not found"))
				.getAmount();
	}
}
