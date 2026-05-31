package com.example.coffeemoney.model.dto;

public class CategorySummaryDto {

	private final String categoryName;
	private final Integer count;
	private final Integer total;

	public CategorySummaryDto(String categoryName, Integer count, Integer total) {
		this.categoryName = categoryName;
		this.count = count;
		this.total = total;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public Integer getCount() {
		return count;
	}

	public Integer getTotal() {
		return total;
	}
}
