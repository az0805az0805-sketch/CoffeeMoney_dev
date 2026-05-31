package com.example.coffeemoney.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.coffeemoney.service.BudgetService;
import com.example.coffeemoney.service.ItemService;
import com.example.coffeemoney.service.RecordService;

@Controller
public class SummaryController {
	private final ItemService itemService;
	private final RecordService recordService;
	private final BudgetService budgetService;

	public SummaryController(ItemService itemService, RecordService recordService,
			BudgetService budgetService) {
		this.itemService = itemService;
		this.recordService = recordService;
		this.budgetService = budgetService;
	}

	@GetMapping("/summary-top")
	public String summaryTop(Model model) {

		model.addAttribute("now", LocalDateTime.now());
		model.addAttribute("items", itemService.findAll());

		int monthlyTotal = recordService.getMonthlyTotal();
		model.addAttribute("monthlyTotal", monthlyTotal);

		int budget = budgetService.getBudget();
		model.addAttribute("balance", budget - monthlyTotal);

		return "summary-top";
	}

	@PostMapping("/add-record")
	public String addRecord(@RequestParam Integer itemId) {
		recordService.addRecord(itemId);
		return "redirect:/summary-top";
	}
}
