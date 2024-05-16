package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	List<Item> findByUserId(Integer userId);

	// SELECT * FROM items WHERE category_id = ?
	List<Item> findByUserIdAndCategoryIdOrderByLimitDateAsc(Integer userId, Integer categoryId);

	List<Item> findByUserIdAndNameContainingOrderByLimitDateAsc(Integer userId, String keyword);

	List<Item> findByUserIdOrderByLimitDateAsc(Integer userId);
}
