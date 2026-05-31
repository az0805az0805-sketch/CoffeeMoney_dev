package com.example.coffeemoney.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

	//itemボタンが押されたらレコード作成
	public void addRecord(Integer itemId) {

		ItemEntity item = itemRepository.findById(itemId)
				.orElseThrow(() -> new IllegalArgumentException("Item not found"));

		RecordEntity record = new RecordEntity();
		record.setItem(item);
		record.setAmount(item.getPrice());
		record.setCreatedAt(LocalDateTime.now());

		recordRepository.save(record);
	}

	public Integer getMonthlyTotal() {
		LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
		LocalDateTime end = start.plusMonths(1);

		return recordRepository.findByCreatedAtBetween(start, end)
				.stream()
				.mapToInt(RecordEntity::getAmount)
				.sum();
	}

	//当月の一覧を返す
	public List<RecordEntity> getMonthlyRecords() {
		LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
		LocalDateTime end = start.plusMonths(1);

		return recordRepository.findByCreatedAtBetween(start, end);
	}

	public void deleteRecord(Integer recordId) {
		recordRepository.deleteById(recordId);
	}

}
