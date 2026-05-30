package com.example.coffeemoney.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Entity
@Data
public class RecordEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	private int amount;

	private LocalDateTime createdAt = LocalDateTime.now();
}
