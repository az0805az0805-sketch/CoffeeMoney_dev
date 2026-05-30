package com.example.coffeemoney.model.dto;

public record ItemSummaryDto(
		Long itemId,
		String itemName,
		Integer price,
		Long count,
		Long total) {

}
