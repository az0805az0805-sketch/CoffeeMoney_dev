package com.example.coffeemoney.model.dto;

public class MonthSummaryDto {
	private final String month;
	private final Integer count;
	private final Integer total;

	public MonthSummaryDto(String month, Integer count, Integer total) {
		this.month = month;
		this.count = count;
		this.total = total;
	}

	public String getMonth() {
		return month;
	}

	public Integer getCount() {
		return count;
	}

	public Integer getTotal() {
		return total;
	}
}
