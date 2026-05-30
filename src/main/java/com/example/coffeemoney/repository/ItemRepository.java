package com.example.coffeemoney.repository;

import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByCategoryId(Long CategoryId);
}
