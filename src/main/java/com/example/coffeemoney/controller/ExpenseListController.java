package com.example.coffeemoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.coffeemoney.service.BudgetService;
import com.example.coffeemoney.service.RecordService;

@Controller
public class ExpenseListController {
	private final RecordService recordService;
	private final BudgetService budgetService;

	public ExpenseListController(RecordService recordService, BudgetService budgetService) {
		this.recordService = recordService;
		this.budgetService = budgetService;
	}

	@GetMapping("/records")
	public String showMonthlyRecords(Model model) {

		// 当月のレコード一覧
		model.addAttribute("records", recordService.getMonthlyRecords());

		// 合計金額
		int monthlyTotal = recordService.getMonthlyTotal();
		model.addAttribute("monthlyTotal", monthlyTotal);

		// 予算
		int budget = budgetService.getBudget();
		model.addAttribute("budget", budget);

		// 残り
		model.addAttribute("balance", budget - monthlyTotal);

		return "records"; // records.html を表示
	}

	//削除リクエストを受け取る
	@PostMapping("/delete-record")
	public String deleteRecord(@RequestParam Integer recordId) {
		recordService.deleteRecord(recordId);
		return "redirect:/records";
	}

}
