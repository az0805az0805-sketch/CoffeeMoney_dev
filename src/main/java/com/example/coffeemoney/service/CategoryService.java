package com.example.coffeemoney.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.coffeemoney.model.entity.CategoryEntity;
import com.example.coffeemoney.model.entity.ItemEntity;
import com.example.coffeemoney.repository.CategoryRepository;
import com.example.coffeemoney.repository.ItemRepository;

@Service
public class CategoryService  {

	private final CategoryRepository categoryRepository;
	private final ItemRepository itemRepository;

	public CategoryService(CategoryRepository categoryRepository,
			ItemRepository itemRepository) {
		this.categoryRepository = categoryRepository;
		this.itemRepository = itemRepository;
	}

	//カテゴリー一覧（home 用）
	public List<CategoryEntity> getAllCategories() {
		return categoryRepository.findAll();
	}

	//カテゴリー1件取得（count 用）
	public CategoryEntity getCategory(Integer categoryId) {
		return categoryRepository.findById(categoryId)
				.orElseThrow(() -> new IllegalArgumentException("Category not found"));
	}

	//カテゴリー追加（CategoryController 用）
	public void addCategory(String name, Integer budget) {
		CategoryEntity category = new CategoryEntity();
		category.setName(name);
		category.setBudget(budget != null ? budget : 0);
		categoryRepository.save(category);
	}

	//カテゴリー削除(紐ずくアイテムがあればアイテムも削除)	
	public void deleteCategory(Integer categoryId) {
		// 先にアイテム削除
		List<ItemEntity> items = itemRepository.findByCategoryId(categoryId);
		itemRepository.deleteAll(items);

		// カテゴリー削除
		categoryRepository.deleteById(categoryId);
	}
	//カテゴリーの更新
	public void updateCategory(Integer id, String name, Integer budget) {

		//既存のカテゴリーを取得（存在しなければ例外）
		var category = categoryRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("カテゴリーが見つかりません: " + id));

		category.setName(name);
		category.setBudget(budget);

		categoryRepository.save(category);
	}

}
