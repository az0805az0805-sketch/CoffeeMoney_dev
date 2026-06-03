package com.example.coffeemoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.coffeemoney.model.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

 @Query("SELECT c FROM CategoryEntity c WHERE c.name = :name")
    CategoryEntity findByName(String name);   // カテゴリー名で検索（重複しない前提）
    
}
