package com.example.coffeemoney.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.coffeemoney.model.dto.CategorySummaryDto;
import com.example.coffeemoney.model.dto.ItemSummaryDto;
import com.example.coffeemoney.model.dto.MonthSummaryDto;
import com.example.coffeemoney.model.entity.RecordEntity;

public interface RecordRepository extends JpaRepository<RecordEntity, Long> {

	// 指定期間の支出一覧（例：月初〜月末）
	List<RecordEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

	// アイテム別の支出一覧
	List<RecordEntity> findByItemId(Long itemId);

	@Query("""
			    SELECT new com.example.coffeemoney.model.dto.ItemSummaryDto(
			        i.id,
			        i.name,
			        i.price,
			        COUNT(r.id),
			        SUM(r.amount)
			    )
			    FROM RecordEntity r
			    JOIN r.item i
			    GROUP BY i.id, i.name, i.price
			    ORDER BY SUM(r.amount) DESC
			""")
	List<ItemSummaryDto> getItemSummary();

	@Query("""
			    SELECT new com.example.coffeemoney.model.dto.CategorySummaryDto(
			        c.id,
			        c.name,
			        COUNT(r.id),
			        SUM(r.amount)
			    )
			    FROM RecordEntity r
			    JOIN r.item i
			    JOIN i.category c
			    GROUP BY c.id, c.name
			    ORDER BY SUM(r.amount) DESC
			""")
	List<CategorySummaryDto> getCategorySummary();

	@Query("""
			    SELECT new com.example.coffeemoney.model.dto.MonthSummaryDto(
			        FUNCTION('TO_CHAR', r.createdAt, 'YYYY-MM'),
			        COUNT(r.id),
			        SUM(r.amount)
			    )
			    FROM RecordEntity r
			    GROUP BY FUNCTION('TO_CHAR', r.createdAt, 'YYYY-MM')
			    ORDER BY FUNCTION('TO_CHAR', r.createdAt, 'YYYY-MM') DESC
			""")
	List<MonthSummaryDto> getMonthSummary();

}
