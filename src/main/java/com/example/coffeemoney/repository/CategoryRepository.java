package com.example.coffeemoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coffeemoney.model.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

	// カテゴリー名で検索（重複しない前提）
	CategoryEntity findByName(String name);
}
