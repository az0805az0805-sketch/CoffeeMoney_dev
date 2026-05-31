package com.example.coffeemoney.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.coffeemoney.model.dto.CategorySummaryDto;
import com.example.coffeemoney.model.dto.ItemSummaryDto;
import com.example.coffeemoney.model.dto.MonthSummaryDto;
import com.example.coffeemoney.model.entity.RecordEntity;

public interface RecordRepository extends JpaRepository<RecordEntity, Integer> {

	// アイテム別の支出一覧
	List<RecordEntity> findByItemId(Integer itemId);

	//レコードの月別かつカテゴリー別一覧
	List<RecordEntity> findByItem_Category_IdAndCreatedAtBetween(
			Integer categoryId,
			LocalDateTime start,
			LocalDateTime end);

	@Query("""
			    SELECT new com.example.coffeemoney.model.dto.ItemSummaryDto(
			        i.id,
			        i.name,
			        i.price,
			        CAST(COUNT(r.id) AS int),
			        CAST(SUM(r.amount) AS int)
			    )
			    FROM RecordEntity r
			    JOIN r.item i
			    GROUP BY i.id, i.name, i.price
			    ORDER BY CAST(SUM(r.amount) AS int) DESC
			""")
	List<ItemSummaryDto> getItemSummary();

	@Query("""
			    SELECT new com.example.coffeemoney.model.dto.CategorySummaryDto(
			        c.name,
			        CAST(COUNT(r.id) AS int),
			        CAST(SUM(r.amount) AS int)
			    )
			    FROM RecordEntity r
			    JOIN r.item i
			    JOIN i.category c
			    GROUP BY c.id, c.name
			    ORDER BY CAST(SUM(r.amount) AS int) DESC
			""")
	List<CategorySummaryDto> getCategorySummary();

	@Query("""
			    SELECT new com.example.coffeemoney.model.dto.MonthSummaryDto(
			        CAST(FUNCTION('TO_CHAR', r.createdAt, 'YYYY-MM') AS string),
			        CAST(COUNT(r.id) AS int),
			        CAST(SUM(r.amount) AS int)
			    )
			    FROM RecordEntity r
			    GROUP BY CAST(FUNCTION('TO_CHAR', r.createdAt, 'YYYY-MM') AS string)
			    ORDER BY CAST(FUNCTION('TO_CHAR', r.createdAt, 'YYYY-MM') AS string) DESC
			""")
	List<MonthSummaryDto> getMonthSummary();

	//月合計を返す
	@Query("""
			    SELECT COALESCE(SUM(r.amount), 0)
			    FROM RecordEntity r
			    WHERE r.createdAt >= :start AND r.createdAt < :end
			""")
	Integer getMonthlyTotal(LocalDateTime start, LocalDateTime end);

	List<RecordEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

	@Query("""
			    SELECT COALESCE(SUM(r.amount), 0)
			    FROM RecordEntity r
			    JOIN r.item i
			    JOIN i.category c
			    WHERE c.id = :categoryId
			      AND r.createdAt >= :start
			      AND r.createdAt < :end
			""")
	Integer getMonthlyTotalByCategory(Integer categoryId, LocalDateTime start, LocalDateTime end);

}
