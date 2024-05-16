package com.example.demo.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

	private final static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 品目ID

	@Column(name = "limit_date")
	private LocalDate limitDate; //賞味期限

	@Column(name = "category_id")
	private Integer categoryId; // カテゴリーID

	@Column(name = "user_id")
	private Integer userId; //ユーザーID

	private String name; // 品目名

	private String memo; // メモ

	//ゲッターとセッター
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLimitDate() {
		return limitDate.format(fmt);
	}

	public void setLimitDate(LocalDate limitDate) {
		this.limitDate = limitDate;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Item() {

	}

}
