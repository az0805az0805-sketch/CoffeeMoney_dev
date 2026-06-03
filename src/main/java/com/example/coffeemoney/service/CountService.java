package com.example.coffeemoney.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.coffeemoney.repository.RecordRepository;

@Service
public class CountService {

	private final RecordRepository recordRepository;

	public CountService(RecordRepository recordRepository) {
		this.recordRepository = recordRepository;
	}

	public Long getThisMonthTotal() {
		LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
		LocalDateTime end = start.plusMonths(1);

		return recordRepository.findByCreatedAtBetween(start, end)
				.stream()
				.mapToLong(r -> r.getAmount())
				.sum();
	}

}
