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

	public List<CategoryEntity> getAllCategories() {
		return categoryRepository.findAll();
	}
}
