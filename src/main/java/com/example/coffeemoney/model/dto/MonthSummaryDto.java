package com.example.coffeemoney.model.dto;

import java.time.YearMonth;

public record MonthSummaryDto(
		YearMonth month,
		Long count,
		Long total,
		Long budget,
		Long balance) {

}
