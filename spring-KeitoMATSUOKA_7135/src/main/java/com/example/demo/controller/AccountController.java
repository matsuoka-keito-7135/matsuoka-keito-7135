package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	Account account;

	@Autowired
	UserRepository userRepository;

	//	TODO
	// ログイン画面を表示
	@GetMapping({ "/", "/login" })
	public String index(
			Model model) {
		// セッション情報を全てクリアする
		session.invalidate();

		return "login";
	}

	//ログアウト処理
	@GetMapping("/logout")
	public String invalidate() {

		session.invalidate();

		return "redirect:/login";
	}

	// ログインを実行
	@PostMapping("/login")
	public String login(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {

		List<User> users = userRepository.findByEmailAndPassword(email, password);

		if (users.size() == 0) {
			model.addAttribute("error", "メールアドレスまたはパスワードが一致しませんでした");
			return "login";
		}

		//Userクラスのオブジェクトに、email・passと一致するインデックス0番目のデータを代入（初期化）
		User user = userRepository.findByEmailAndPassword(email, password).get(0);

		//ログイン情報が一致する場合、セッション管理されたアカウント情報に名前をセット
		account.setName(user.getName());
		account.setId(user.getId());

		// 「/items」へのリダイレクト
		return "redirect:/items";
	}

	//ユーザー登録画面を表示
	@GetMapping("/users/new")
	public String create(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "addUser";
	}

	//新規会員登録を実行した際にログインページに遷移
	@PostMapping("/users/add")
	public String add(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "password_confirm", defaultValue = "") String passwordConfirm,
			Model model) {

		List<String> errorList = new ArrayList<String>();
		User user = new User(name, email, password);

		if ("".equals(name)) {
			errorList.add("名前は必須です");
		}

		if ("".equals(email)) {
			errorList.add("メールアドレスは必須です");
			//emailがcustomersテーブルに存在する場合、検索結果は1以上になる
		} else if (userRepository.findByEmail(email).size() > 0) {
			errorList.add("登録済みのメールアドレスです");
		}

		if ("".equals(password)) {
			errorList.add("パスワードは必須です");
		} else if (!(password.equals(passwordConfirm))) {
			errorList.add("パスワードが一致しませんでした");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("user", user);
			return "addUser";
		}

		userRepository.save(user);

		return "redirect:/login";
	}

}
