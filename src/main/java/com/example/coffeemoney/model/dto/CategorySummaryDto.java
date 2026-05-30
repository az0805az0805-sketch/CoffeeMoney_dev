package com.example.coffeemoney.model.dto;

public record CategorySummaryDto(
		Long categoryId,
		String categoryName,
		Long count,
		Long total) {

}
