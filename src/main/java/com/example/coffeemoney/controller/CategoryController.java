package com.example.coffeemoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.coffeemoney.service.CategoryService;

@Controller
public class CategoryController {
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/category-add")
	public String showAddCategoryForm() {
		return "category-add";
	}

	@PostMapping("/add-category")
	public String addCategory(
			@RequestParam String name,
			@RequestParam(required = false) Integer budget) {
		categoryService.addCategory(name, budget);
		return "redirect:/";
	}

	//カテゴリー削除
	@PostMapping("/category/delete")
	public String deleteCategory(@RequestParam Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		return "redirect:/";
	}

}
