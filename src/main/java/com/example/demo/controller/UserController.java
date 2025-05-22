package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Guest;
import com.example.demo.model.GuestModel;
import com.example.demo.repository.GuestRepository;

@Controller
public class UserController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	GuestRepository guestRepository;
	
	@Autowired
	GuestModel guestModel;
	
	//ログイン画面表示
	@GetMapping({"/","/login","/logout"})
	public String index() {
		
		session.invalidate();
		return "guestLogin";
	}

	
	//ログイン処理
	@PostMapping("/login")
	public String login(			
			@RequestParam(name="email", defaultValue = "") String email,
			@RequestParam(name="password", defaultValue = "") String password,
			Model model) {
		
		// メールアドレスとパスが一致するデータの存在チェック
		Optional<Guest> guestData = guestRepository.findByEmailAndPassword(email, password);
		
		//エラーリストの準備
		List<String> errorList = new ArrayList<>();
		
		//データの取得可否で場合分け
		// できなかった
		//    →記入漏れ、一致不可 → 1 
		// できた→2
		
		// 1. →エラー
		if(email == null || email.length() == 0) {
			errorList.add("メールアドレスは必須です");
		}
		if(password == null || password.length() == 0) {
			errorList.add("パスワードは必須です");
		}
		
		if(email.length() > 0 && password.length() > 0) {
			if(guestData.isEmpty()) {
				
				errorList.add( "メールアドレスとパスワードが一致しませんでした");
			}
			else {
				
				// 2.
				Guest guest = guestData.get();
				// ログインしたユーザーの名前をセッションに登録
				guestModel.setName(guest.getName());
				guestModel.setId(guest.getId());
			}
		}

		if(errorList.size() > 0 ) {
			model.addAttribute("errorList", errorList);
			return "guestLogin";
		}
		
		return "redirect:/thread";
	}
	
	
	//===新規登録===
	
	//新規登録画面表示
	@GetMapping("/user/join")
	public String add() {
		return "newGuest";
	}
	
	//新規登録フォーム
	@PostMapping("/user/join")
	public String join(
			@RequestParam(name="name", defaultValue = "") String name,
			@RequestParam(name="email", defaultValue = "") String email,
			@RequestParam(name="password", defaultValue = "") String password,
			@RequestParam(name="passwordConfirm", defaultValue = "") String passwordConfirm,
			Model model) {
		
		//
//		Guest guest = new Guest();
		
		//エラーリストの準備
		List<String> errorList = new ArrayList<>();
		
		//エラー
		//名前
		if (name == null || name.equals("")) {
			errorList.add("ニックネームは必須です");
		}
		else if (name.length() > 20) {
			errorList.add("ニックネームは20文字以内です");
		}
//		else {
//			guest.setName(name);
//		}
		
		//メール
		if (email == null || email.length() == 0) {
			errorList.add("メールアドレスは必須です");
		}else if (email.length() > 50) {
			errorList.add("メールアドレスは50文字以内にしてください");
		}
		
		//パスワード
		if (password == null || password.length() == 0) {
			errorList.add("パスワードは必須です");
		}
		else if (password.length() > 20) {
			errorList.add("パスワードは20文字以内です");
		}
		
		else {
			if(!password.equals(passwordConfirm)) {
			errorList.add("パスワードが一致しませんでした");
			}
		}
		
		if(errorList.size() > 0 ) {
			model.addAttribute("errorList", errorList);
			return "newGuest";
		}
		
		//DB登録
		Guest guest = new Guest(email, password, name);
		guestRepository.save(guest);
		return "guestLogin";
	}
}
