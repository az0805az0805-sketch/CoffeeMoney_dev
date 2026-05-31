package com.example.coffeemoney.model.dto;

public class ItemSummaryDto {

	private final Integer itemId;
	private final String itemName;
	private final Integer itemPrice;
	private final Integer count;
	private final Integer total;

	public ItemSummaryDto(Integer itemId, String itemName, Integer itemPrice, Integer count, Integer total) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.count = count;
		this.total = total;
	}

	public Integer getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public Integer getItemPrice() {
		return itemPrice;
	}

	public Integer getCount() {
		return count;
	}

	public Integer getTotal() {
		return total;
	}
}
