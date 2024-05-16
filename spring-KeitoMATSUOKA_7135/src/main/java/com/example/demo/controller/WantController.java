package com.example.demo.controller;

import java.time.LocalDate;
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
public class WantController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	Account account;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	WantRepository wantRepository;

	// 買い物リスト一覧表示
	@GetMapping("/wants")
	public String index(Model model) {

		//TODO
		Integer userId = account.getId();

		// 全カテゴリー一覧を取得
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		// 買い物リスト一覧情報の取得
		List<Want> wantList = wantRepository.findByUserId(userId);
		model.addAttribute("wants", wantList);

		return "wants";
	}

	//新規買い物リスト登録
	@GetMapping("/wants/new")
	public String create(Model model) {
		// 全カテゴリー一覧を取得
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		Want want = new Want();

		model.addAttribute("want", want);
		return "addWant";
	}

	//買い物リストをitemsテーブルに追加
	@PostMapping("/wants/add")
	public String store(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			Model model) {

		List<String> errorList = new ArrayList<String>();
		Want want = new Want(name, categoryId, account.getId());

		if ("".equals(name)) {
			errorList.add("品目名が空欄になっています");
		}

		if (errorList.size() > 0) {
			List<Category> categories = categoryRepository.findAll();
			model.addAttribute("errorList", errorList);
			model.addAttribute("want", want);
			model.addAttribute("categories", categories);
			return "addItem";
		}

		wantRepository.save(want);
		return "redirect:/wants";
	}

	//買い物リスト情報を編集する画面に遷移
	@GetMapping("/wants/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			Model model) {
		Want want = wantRepository.findById(id).get();
		model.addAttribute("want", want);

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		return "editWant";
	}

	//買い物リスト情報を更新
	@PostMapping("/wants/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "limitDate", defaultValue = "") LocalDate limitDate,
			@RequestParam(value = "memo", defaultValue = "") String memo,
			Model model) {

		Want want = new Want(id, name, categoryId, account.getId());
		wantRepository.save(want);
		return "redirect:/wants";
	}

	//品目をitemsテーブルから削除
	@PostMapping("/wants/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id,
			Model model) {

		wantRepository.deleteById(id);
		return "redirect:/wants";
	}

	//買い物リストを品目一覧に登録するためのページに遷移
	@GetMapping("/wants/{id}/addItem")
	public String newItem(
			@PathVariable("id") Integer id,
			Model model) {

		Want want = wantRepository.findById(id).get();
		model.addAttribute("want", want);

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		return "itemToWant";

	}

	//itemsテーブルに追加するとともに、wantsテーブルから該当品目を削除
	@PostMapping("/wants/{id}/addItem")
	public String addItemt(
			@PathVariable("id") Integer id,
			@RequestParam(value = "limitDate", defaultValue = "") LocalDate limitDate,
			@RequestParam(value = "memo", defaultValue = "") String memo,
			Model model) {

		Want want = wantRepository.findById(id).get();
		Item item = new Item(want.getName(), want.getCategoryId(), limitDate, memo, want.getUserId());

		itemRepository.save(item);

		wantRepository.deleteById(id);

		return "redirect:/wants";
	}

}
