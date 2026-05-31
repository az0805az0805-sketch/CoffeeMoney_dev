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

	public RecordService(RecordRepository recordRepository,
			ItemRepository itemRepository) {
		this.recordRepository = recordRepository;
		this.itemRepository = itemRepository;
	}

	/* ============================
	   1. レコード作成
	   ============================ */
	public void addRecord(Integer itemId) {

		ItemEntity item = itemRepository.findById(itemId)
				.orElseThrow(() -> new IllegalArgumentException("Item not found"));

		RecordEntity record = new RecordEntity();
		record.setItem(item);
		record.setAmount(item.getPrice());
		record.setCreatedAt(LocalDateTime.now());

		recordRepository.save(record);
	}

	/* ============================
	   2. 当月の期間取得（共通化）
	   ============================ */
	private LocalDateTime getMonthStart() {
		return LocalDate.now().withDayOfMonth(1).atStartOfDay();
	}

	private LocalDateTime getMonthEnd() {
		return getMonthStart().plusMonths(1);
	}

	/* ============================
	   3. 当月の合計（全体）
	   ============================ */
	public Integer getMonthlyTotal() {
		LocalDateTime start = getMonthStart();
		LocalDateTime end = getMonthEnd();

		return recordRepository.findByCreatedAtBetween(start, end)
				.stream()
				.mapToInt(RecordEntity::getAmount)
				.sum();
	}

	/* ============================
	   4. 当月の合計（カテゴリー別）
	   ============================ */
	public Integer getMonthlyTotalByCategory(Integer categoryId) {
		LocalDateTime start = getMonthStart();
		LocalDateTime end = getMonthEnd();

		return recordRepository.getMonthlyTotalByCategory(categoryId, start, end);
	}

	/* ============================
	   5. 当月のレコード一覧（全体）
	   ============================ */
	public List<RecordEntity> getMonthlyRecords() {
		return recordRepository.findByCreatedAtBetween(getMonthStart(), getMonthEnd());
	}

	/* ============================
	   6. 当月のレコード一覧（カテゴリー別）
	   ============================ */
	public List<RecordEntity> getMonthlyRecords(Integer categoryId) {
		LocalDateTime start = getMonthStart();
		LocalDateTime end = getMonthEnd();
		return recordRepository.findByItem_Category_IdAndCreatedAtBetween(categoryId,
				start,
				end);

	}

	/* ============================
	   7. レコード削除
	   ============================ */
	public void deleteRecord(Integer recordId) {
		recordRepository.deleteById(recordId);
	}
}