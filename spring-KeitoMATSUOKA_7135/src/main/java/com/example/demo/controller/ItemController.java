package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.example.demo.entity.Want;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.WantRepository;

@Controller
public class ItemController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	Account account;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	WantRepository wantRepository;

	// 品目一覧表示
	@GetMapping("/items")
	public String index(
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			Model model) {

		//TODO
		Integer userId = account.getId();

		// 全カテゴリー一覧を取得
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		// 品目一覧情報の取得
		List<Item> itemList = null;
		if (categoryId == null) {
			itemList = itemRepository.findByUserIdOrderByLimitDateAsc(userId);
		} else {
			// itemsテーブルをカテゴリーIDを指定して一覧を取得
			itemList = itemRepository.findByUserIdAndCategoryIdOrderByLimitDateAsc(userId, categoryId);
		}

		if ("".equals(keyword)) {

		} else {
			itemList = itemRepository.findByUserIdAndNameContainingOrderByLimitDateAsc(userId, keyword);
		}

		model.addAttribute("items", itemList);

		return "items";
	}

	//新規品目登録
	@GetMapping("/items/new")
	public String create(Model model) {
		// 全カテゴリー一覧を取得
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		LocalDate today = LocalDate.now();
		model.addAttribute("limitDate", today);

		Item item = new Item();

		model.addAttribute("item", item);
		return "addItem";
	}

	//品目をitemsテーブルに追加
	@PostMapping("/items/add")
	public String store(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "limitDate", defaultValue = "") LocalDate limitDate,
			@RequestParam(value = "memo", defaultValue = "") String memo,
			Model model) {

		List<String> errorList = new ArrayList<String>();
		Item item = new Item(name, categoryId, limitDate, memo, account.getId());
		LocalDate today = LocalDate.now();

		if ("".equals(name)) {
			errorList.add("品目名が空欄になっています");
		}

		if (limitDate.isBefore(today)) {
			errorList.add("賞味期限が今日の日付よりも前になっています");
		}

		if (errorList.size() > 0) {
			List<Category> categories = categoryRepository.findAll();
			model.addAttribute("errorList", errorList);
			model.addAttribute("item", item);
			model.addAttribute("categories", categories);
			model.addAttribute("limitDate", limitDate);
			return "addItem";
		}

		itemRepository.save(item);
		return "redirect:/items";
	}

	//品目情報を編集する画面に遷移
	@GetMapping("/items/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			Model model) {
		Item item = itemRepository.findById(id).get();
		model.addAttribute("item", item);

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		LocalDate limitDate = LocalDate.parse(item.getLimitDate().replace("/", ""),
				DateTimeFormatter.ofPattern("yyyyMMdd"));
		model.addAttribute("limitDate", limitDate);
		return "editItem";
	}

	//品目情報を更新
	@PostMapping("/items/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "limitDate", defaultValue = "") LocalDate limitDate,
			@RequestParam(value = "memo", defaultValue = "") String memo,
			Model model) {

		Item item = new Item(id, name, categoryId, limitDate, memo, account.getId());
		itemRepository.save(item);
		return "redirect:/items";
	}

	//品目をitemsテーブルから削除
	@PostMapping("/items/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id,
			Model model) {

		itemRepository.deleteById(id);
		return "redirect:/items";
	}

	//wantsテーブルに品目を追加
	@PostMapping("/items/{id}/addWant")
	public String addWant(
			@PathVariable("id") Integer id,
			Model model) {

		Item item = itemRepository.findById(id).get();
		Want want = new Want(item.getName(), item.getCategoryId(), item.getUserId());

		wantRepository.save(want);

		return "redirect:/items";
	}
}
