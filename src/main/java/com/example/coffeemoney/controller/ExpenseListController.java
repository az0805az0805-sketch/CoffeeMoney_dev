package com.example.coffeemoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.coffeemoney.service.BudgetService;
import com.example.coffeemoney.service.CategoryService;
import com.example.coffeemoney.service.RecordService;

@Controller
public class ExpenseListController {
	private final RecordService recordService;
	private final BudgetService budgetService;
	private final CategoryService categoryService;

	public ExpenseListController(RecordService recordService, BudgetService budgetService,
			CategoryService categoryService) {
		this.recordService = recordService;
		this.budgetService = budgetService;
		this.categoryService = categoryService;
	}

	@GetMapping("/records")
	public String showMonthlyRecords(@RequestParam Integer categoryId, Model model) {

		var category = categoryService.getCategory(categoryId);

		// 当月のレコード一覧（カテゴリー別）
		model.addAttribute("records", recordService.getMonthlyRecords(categoryId));

		// 合計金額（カテゴリー別）
		int monthlyTotal = recordService.getMonthlyTotalByCategory(categoryId);
		model.addAttribute("monthlyTotal", monthlyTotal);

		// 予算（カテゴリー別）
		int budget = category.getBudget();
		model.addAttribute("budget", budget);

		// 残り
		model.addAttribute("balance", budget - monthlyTotal);

		// 戻るボタン用
		model.addAttribute("categoryId", categoryId);

		return "records";
	}

	//削除リクエストを受け取る
	@PostMapping("/delete-record")
	public String deleteRecord(
			@RequestParam Integer recordId,
			@RequestParam Integer categoryId) {

		recordService.deleteRecord(recordId);

		return "redirect:/records?categoryId=" + categoryId;
	}

}
