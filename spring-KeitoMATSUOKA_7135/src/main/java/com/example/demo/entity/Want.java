package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wants")
public class Want {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 買い物レコードID

	@Column(name = "category_id")
	private Integer categoryId; // カテゴリーID

	@Column(name = "user_id")
	private Integer userId; // ユーザーID

	private String name; // 買い物レコードの品目名

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Want() {

	}

	public Want(String name, Integer categoryId, Integer userId) {
		this.name = name;
		this.categoryId = categoryId;
		this.userId = userId;
	}

	public Want(Integer id, String name, Integer categoryId, Integer userId) {
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.userId = userId;
	}

}
