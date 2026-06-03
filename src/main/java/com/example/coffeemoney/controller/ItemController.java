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
	public String showAddItemForm(@RequestParam Integer categoryId,
			Model model) {
		model.addAttribute("categoryId", categoryId);
		return "item-add";
	}

	@PostMapping("/add-item")
	public String addItem(
			@RequestParam String name,
			@RequestParam Integer price,
			@RequestParam Integer categoryId) {
		itemService.addItem(name, price, categoryId);
		return "redirect:/count?categoryId=" + categoryId;
	}

	//更新画面表示
	@GetMapping("/item/edit")
	public String editItem(@RequestParam Integer id, Model model) {
		var item = itemService.getItem(id);
		model.addAttribute("item", item);
		return "edit-item";
	}
	//更新処理
	@PostMapping("/item/update")
	public String updateItem(
			@RequestParam Integer id,
			@RequestParam Integer categoryId,
			@RequestParam String name,
			@RequestParam Integer price) {

		itemService.updateItem(id, name, price);

		return "redirect:/count?categoryId=" + categoryId;
	}
	//アイテム削除
	@PostMapping("/item/delete")
	public String deleteItem(
	        @RequestParam Integer id,
	        @RequestParam Integer categoryId) {

	    itemService.deleteItem(id);

	    return "redirect:/count?categoryId=" + categoryId;
	}


}
