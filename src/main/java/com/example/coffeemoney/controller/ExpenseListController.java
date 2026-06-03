package com.example.coffeemoney.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.coffeemoney.service.CategoryService;
import com.example.coffeemoney.service.RecordService;

@Controller
public class ExpenseListController {
	private final RecordService recordService;
	private final CategoryService categoryService;

	public ExpenseListController(RecordService recordService,
			CategoryService categoryService) {
		this.recordService = recordService;
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

	@GetMapping("/month-select")
	public String showMonthSelect(@RequestParam Integer categoryId,
			Model model) {

		model.addAttribute("categoryId", categoryId);

		// 年リスト（例：2020〜現在）
		List<Integer> years = IntStream.rangeClosed(2026, LocalDate.now().getYear())
				.boxed()
				.collect(Collectors.toList());

		// 月リスト（1〜12）
		List<Integer> months = IntStream.rangeClosed(1, 12)
				.boxed()
				.collect(Collectors.toList());

		model.addAttribute("years", years);
		model.addAttribute("months", months);

		return "month-select";
	}

	@GetMapping("/records-by-month")
	public String showRecordsByMonth(
			@RequestParam Integer categoryId,
			@RequestParam Integer year,
			@RequestParam Integer month,
			Model model) {

		var category = categoryService.getCategory(categoryId);

		// 指定された年月のレコード一覧（カテゴリー別）
		model.addAttribute("records", recordService.getRecordsByCategoryAndMonth(categoryId, year, month));

		// 合計金額（カテゴリー別）
		int monthlyTotal = recordService.getMonthlyTotalByCategoryAndMonth(categoryId, year, month);
		model.addAttribute("monthlyTotal", monthlyTotal);

		// 予算（カテゴリー別）
		int budget = category.getBudget();
		model.addAttribute("budget", budget);

		// 残り
		model.addAttribute("balance", budget - monthlyTotal);

		// 戻るボタン用
		model.addAttribute("categoryId", categoryId);

		// 当月の詳細画面をそのまま使う
		return "records";
	}

}
