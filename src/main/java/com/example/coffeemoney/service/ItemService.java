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
}
