package com.example.coffeemoney.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coffeemoney.model.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {

	List<ItemEntity> findByCategoryId(Integer CategoryId);
}
