package com.example.coffeemoney.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.coffeemoney.model.entity.CategoryEntity;
import com.example.coffeemoney.repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	//カテゴリー一覧（home 用）
	public List<CategoryEntity> getAllCategories() {
		return categoryRepository.findAll();
	}

	//カテゴリー1件取得（summary-top 用）
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
}
