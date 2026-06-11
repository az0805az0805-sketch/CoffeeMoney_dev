package com.example.coffeemoney.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.coffeemoney.model.entity.CategoryEntity;
import com.example.coffeemoney.model.entity.ItemEntity;
import com.example.coffeemoney.repository.CategoryRepository;
import com.example.coffeemoney.repository.ItemRepository;
import com.example.coffeemoney.repository.RecordRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final ItemRepository itemRepository;
	private final RecordRepository recordRepository;
	


	public CategoryService(CategoryRepository categoryRepository,
			ItemRepository itemRepository,
			RecordRepository recordRepository) {
		this.categoryRepository = categoryRepository;
		this.itemRepository = itemRepository;
		this.recordRepository = recordRepository;
	}

	public List<CategoryEntity> getAllCategories() {
		return categoryRepository.findAll();
	}

	public CategoryEntity getCategory(Integer categoryId) {
		return categoryRepository.findById(categoryId)
				.orElseThrow(() -> new IllegalArgumentException("Category not found"));
	}

	public void addCategory(String name, Integer budget) {
		CategoryEntity category = new CategoryEntity();
		category.setName(name);
		category.setBudget(budget != null ? budget : 0);
		categoryRepository.save(category);
	}

	@Transactional
	public void deleteCategory(Integer categoryId) {

		// ① カテゴリー取得
		CategoryEntity category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new IllegalArgumentException("Category not found"));

		// ② アイテム一覧取得
		List<ItemEntity> items = itemRepository.findByCategoryId(categoryId);

		// ③ アイテムに紐づくレコード削除
		for (ItemEntity item : items) {
			recordRepository.deleteByItemId(item.getId());
		}

		// ④ アイテム削除
		itemRepository.deleteByCategoryId(categoryId);

		// ⑤ カテゴリー削除
		categoryRepository.delete(category);
	}

	public void updateCategory(Integer id, String name, Integer budget) {

		var category = categoryRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("カテゴリーが見つかりません: " + id));

		category.setName(name);
		category.setBudget(budget);

		categoryRepository.save(category);
	}
}
