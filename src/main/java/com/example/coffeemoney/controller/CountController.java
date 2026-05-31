package com.example.coffeemoney.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.coffeemoney.model.entity.ItemEntity;
import com.example.coffeemoney.repository.ItemRepository;
import com.example.coffeemoney.service.CategoryService;
import com.example.coffeemoney.service.RecordService;

@Controller
public class CountController {

	private final ItemRepository itemRepository;
	private final RecordService recordService;
	private final CategoryService categoryService;

	public CountController(ItemRepository itemRepository,
			RecordService recordService,
			CategoryService categoryService) {
		this.itemRepository = itemRepository;
		this.recordService = recordService;
		this.categoryService = categoryService;
	}
	//ホーム画面
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		return "home";
	}

	//カウント画面（summary-top）
	@GetMapping("/summary-top")
	public String showSummary(
			@RequestParam Integer categoryId,
			Model model) {

		var category = categoryService.getCategory(categoryId);
		model.addAttribute("category", category);

		List<ItemEntity> items = itemRepository.findByCategoryId(categoryId);
		model.addAttribute("items", items);

		int monthlyTotal = recordService.getMonthlyTotalByCategory(categoryId);
		model.addAttribute("monthlyTotal", monthlyTotal);

		model.addAttribute("now", LocalDateTime.now());

		return "summary-top";
	}

	//レコード追加
	@PostMapping("/record/add")
	public String addRecord(
			@RequestParam Integer itemId,
			@RequestParam Integer categoryId) {

		recordService.addRecord(itemId);

		return "redirect:/summary-top?categoryId=" + categoryId;
	}
}
