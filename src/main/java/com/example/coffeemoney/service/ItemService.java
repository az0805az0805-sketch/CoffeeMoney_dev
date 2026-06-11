package com.example.coffeemoney.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.coffeemoney.model.entity.CategoryEntity;
import com.example.coffeemoney.model.entity.ItemEntity;
import com.example.coffeemoney.repository.CategoryRepository;
import com.example.coffeemoney.repository.ItemRepository;

@Service
public class ItemService {

	private final ItemRepository itemRepository;
	private final CategoryRepository categoryRepository;

	public ItemService(ItemRepository itemRepository,
			CategoryRepository categoryRepository) {
		this.itemRepository = itemRepository;
		this.categoryRepository = categoryRepository;
	}

	public List<ItemEntity> findAll() {
		return itemRepository.findAll();
	}

	public void addItem(String name, Integer price, Integer categoryId) {

		CategoryEntity category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new IllegalArgumentException("Category not found"));

		ItemEntity item = new ItemEntity();
		item.setName(name);
		item.setPrice(price);
		item.setCategory(category);

		itemRepository.save(item);
	}

	//アイテムを取得
	public ItemEntity getItem(Integer id) {
		return itemRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("アイテムが見つかりません: " + id));
	}

	//既存のitemを更新
	public void updateItem(Integer id, String name, Integer price) {

		var item = itemRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("アイテムが見つかりません: " + id));

		item.setName(name);
		item.setPrice(price);

		itemRepository.save(item);
	}

	//アイテム削除
	public Integer softDeleteItem(Integer id) {
		ItemEntity item = itemRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("アイテムが見つかりません: " + id));

		item.setDeleted(true); // ← 論理削除フラグを立てる
		itemRepository.save(item);
		return item.getCategory().getId();
	}

}