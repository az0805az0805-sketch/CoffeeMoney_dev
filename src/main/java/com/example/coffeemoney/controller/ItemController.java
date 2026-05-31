package com.example.coffeemoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.coffeemoney.service.CategoryService;
import com.example.coffeemoney.service.ItemService;

@Controller
public class ItemController {
	private final ItemService itemService;
	private final CategoryService categoryService;

	public ItemController(ItemService itemService, CategoryService categoryService) {
		this.itemService = itemService;
		this.categoryService = categoryService;
	}

	@GetMapping("/item-add")
	public String showAddItemForm(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		return "item-add";
	}

	@PostMapping("/add-item")
	public String addItem(
			@RequestParam String name,
			@RequestParam Integer price,
			@RequestParam Integer categoryId) {
		itemService.addItem(name, price, categoryId);
		return "redirect:/summary-top";
	}
}
