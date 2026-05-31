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
import com.example.coffeemoney.service.RecordService;
import com.example.coffeemoney.service.SummaryService;

@Controller
public class CountController {
	private final ItemRepository itemRepository;
	private final RecordService recordService;
	private final SummaryService summaryService;

	public CountController(ItemRepository itemRepository,
			RecordService recordService,
			SummaryService summaryService) {
		this.itemRepository = itemRepository;
		this.recordService = recordService;
		this.summaryService = summaryService;
	}

	// カテゴリー別のカウント画面
	@GetMapping("/count")
	public String countPage(
			@RequestParam Long categoryId,
			Model model) {

		List<ItemEntity> items = itemRepository.findByCategoryId(categoryId);

		model.addAttribute("now", LocalDateTime.now());
		model.addAttribute("items", items);
		model.addAttribute("categoryId", categoryId);

		model.addAttribute("monthlyTotal", summaryService.getThisMonthTotal());
		model.addAttribute("balance", summaryService.getThisMonthBalance());

		return "count";
	}

	// ボタン押下 → レコード作成
	@PostMapping("/record/add")
	public String addRecord(
			@RequestParam Long itemId,
			@RequestParam Long categoryId) {

		recordService.addRecord(itemId);

		// 同じカテゴリー画面に戻る
		return "redirect:/count?categoryId=" + categoryId;
	}

}
