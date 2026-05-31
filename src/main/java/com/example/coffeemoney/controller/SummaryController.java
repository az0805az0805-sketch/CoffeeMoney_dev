package com.example.coffeemoney.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.coffeemoney.service.ItemService;
import com.example.coffeemoney.service.RecordService;

@Controller
public class SummaryController {
	private final ItemService itemService;
	private final RecordService recordService;

	public SummaryController(ItemService itemService, RecordService recordService) {
		this.itemService = itemService;
		this.recordService = recordService;
	}

	@GetMapping("/summary-top")
	public String summaryTop(Model model) {

		model.addAttribute("now", LocalDateTime.now());
		model.addAttribute("items", itemService.findAll());

		int monthlyTotal = recordService.getMonthlyTotal();
		model.addAttribute("monthlyTotal", monthlyTotal);

		int budget = 10000; // 必要なら DB に移動
		model.addAttribute("balance", budget - monthlyTotal);

		return "summary-top";
	}
}
