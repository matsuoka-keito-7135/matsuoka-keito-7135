package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ItemRepository itemRepository;

	// 商品一覧表示
	@GetMapping("/items")
	public String index(
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			Model model) {

		// 全カテゴリー一覧を取得
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		// 商品一覧情報の取得
		List<Item> itemList = null;
		if (categoryId == null) {
			itemList = itemRepository.findAllByOrderByLimitDateAsc();
		} else {
			// itemsテーブルをカテゴリーIDを指定して一覧を取得
			itemList = itemRepository.findAllByCategoryIdOrderByLimitDateAsc(categoryId);
		}

		if ("".equals(keyword)) {

		} else {
			itemList = itemRepository.findAllByNameContainingOrderByLimitDateAsc(keyword);
		}

		model.addAttribute("items", itemList);

		return "items";
	}

	//新規品目登録
	@GetMapping("/items/new")
	public String create() {
		return "addItem";
	}

	//TODO
	//品目をitemsテーブルに追加
	@PostMapping("/items/add")
	public String store(
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "limitDate", defaultValue = "") String limitDate,
			@RequestParam(value = "memo", defaultValue = "") String memo,
			Model model) {
		Item item = new Item(categoryId, name, limitDate, memo);
		itemRepository.save(item);
		return "redirect:/items";
	}

	@GetMapping("/items/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			Model model) {
		Item item = itemRepository.findById(id).get();
		model.addAttribute("item", item);
		return "editItem";
	}

	@PostMapping("/items/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "price", defaultValue = "") Integer price,
			Model model) {
		Item item = new Item(id, categoryId, name, price);
		itemRepository.save(item);
		return "redirect:/items";
	}

	@PostMapping("/items/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id,
			Model model) {

		itemRepository.deleteById(id);
		return "redirect:/items";
	}
}
