package com.example.coffeemoney.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.coffeemoney.model.entity.ItemEntity;
import com.example.coffeemoney.model.entity.RecordEntity;
import com.example.coffeemoney.repository.ItemRepository;
import com.example.coffeemoney.repository.RecordRepository;

@Service
public class RecordService {
	private final RecordRepository recordRepository;
	private final ItemRepository itemRepository;

	public RecordService(RecordRepository recordRepository, ItemRepository itemRepository) {
		this.recordRepository = recordRepository;
		this.itemRepository = itemRepository;
	}

	public void addRecord(Long itemId) {

		ItemEntity item = itemRepository.findById(itemId)
				.orElseThrow();

		RecordEntity record = new RecordEntity();
		record.setItem(item);
		record.setAmount(item.getPrice());
		record.setCreatedAt(LocalDateTime.now());

		recordRepository.save(record);
	}
}
