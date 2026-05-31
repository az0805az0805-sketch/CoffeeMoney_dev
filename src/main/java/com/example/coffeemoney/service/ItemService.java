package com.example.coffeemoney.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.coffeemoney.model.entity.ItemEntity;
import com.example.coffeemoney.repository.ItemRepository;

@Service
public class ItemService {

	private final ItemRepository itemRepository;

	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public List<ItemEntity> findAll() {
		return itemRepository.findAll();
	}
}
