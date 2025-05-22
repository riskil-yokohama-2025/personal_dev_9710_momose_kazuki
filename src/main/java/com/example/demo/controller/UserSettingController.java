package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Guest;
import com.example.demo.model.GuestModel;
import com.example.demo.repository.GuestRepository;

@Controller
public class UserSettingController {
	
	@Autowired
	GuestModel guestModel;
	
	@Autowired
	GuestRepository guestRepository;
	
	
	@GetMapping("/user/setting/{tab}")
	public String index(
			@PathVariable(name="tab") String tab,
			Model model) {
		
		//ログインしてない時のアクセス不可
		if(guestModel.getId() == null) {
			return "redirect:/";
		}

		// 対象のデータを取得
		Integer id = guestModel.getId();
		Optional<Guest> guestDbData = guestRepository.findById(id);
		//データ有無のチェック
		if (guestDbData.isEmpty()) {
			return "redirect:/";
		}
		Guest guest = guestDbData.get();
		model.addAttribute("guest", guest);
		
		switch (tab) {
		//case "account":
			//return "settingAccount";
		case "name":
			return "settingName";
		case "email":
			return "settingEmail";
		case "password":
			return "settingPassword";
		default:
			return "settingAccount";
		}
	}
	
	
	//＝＝ニックネームを変更＝＝
	//DB変更
	@PostMapping("/user/setting/name")
	public String updateName(
			@RequestParam(name = "name", defaultValue = "") String name,
			Model model) {
		
		//DBデータの取得
		Optional<Guest> guestDbData = guestRepository.findById(guestModel.getId());
		List<String> errorList = new ArrayList<String>();
		
		if(guestDbData.isEmpty()) {
			return "redirect:/";
		}
		
		Guest guest = guestDbData.get();
		
		if (name == null || name.length() == 0) {
			errorList.add("ニックネームは必須です");
		} else if (name.length() > 20) {
			errorList.add("ニックネームは20文字以内にしてください");
		}
		
		
		//エラーがある場合
		if(errorList.size() > 0 ) {
			model.addAttribute("guest", guest);
			model.addAttribute("errorList", errorList);
			return "settingName";
		}
		
		// DB変更
		guest.setName(name);
		guest.setUpdateUserId(1);
		guest.setUpdateDate(LocalDateTime.now());
		
		guestRepository.save(guest);
		guestModel.setName(name);
	
		return "redirect:/user/setting/account";
	}
	

	//＝＝メールアドレスを変更＝＝
	//DB変更
	@PostMapping("/user/setting/email")
	public String updateEmail(
			@RequestParam(name="email") String email,
			Model model) {
		
		//DBデータの取得
		Optional<Guest> guestDbData = guestRepository.findById(guestModel.getId());
		List<String> errorList = new ArrayList<String>();
		
		if(guestDbData.isEmpty()) {
			return "redirect:/";
		}
		
		Guest guest = guestDbData.get();
		
		if (email == null || email.length() == 0) {
			errorList.add("メールアドレスは必須です");
		}else if (email.length() > 50) {
			errorList.add("メールアドレスは50文字以内にしてください");
		}
		
		//エラーがある場合
		if(errorList.size() > 0 ) {
			model.addAttribute("guest", guest);
			model.addAttribute("errorList", errorList);
			return "settingEmail";
		}
		
		// DB変更
			guest.setEmail(email);
			guest.setUpdateUserId(1);
			guest.setUpdateDate(LocalDateTime.now());
			
			guestRepository.save(guest);
		return "redirect:/user/setting/account";
	}
	
	
	//＝＝メールアドレスを変更＝＝
	//DB変更
	
	@PostMapping("/user/setting/password")
	public String updatePassword(
			@RequestParam(name="oldPassword") String oldPassword,
			@RequestParam(name="password") String password,
			@RequestParam(name="passwordConfirm") String passwordConfirm,
			Model model) {
		
		//DBデータの取得
		Optional<Guest> guestDbData = guestRepository.findById(guestModel.getId());
		List<String> errorList = new ArrayList<String>();
		
		if(guestDbData.isEmpty()) {
			return "redirect:/";
		}
		
		Guest guest = guestDbData.get();
		

		if (oldPassword == null || oldPassword.length() == 0) {
			errorList.add("現在のパスワードは必須です");
		} 
		else {
			String nowPassword = guestRepository.findPasswordById(guestModel.getId());
			if (!oldPassword.equals(nowPassword)) {
				errorList.add("現在のパスワードが異なります");
			} 
		}
		
		if (password == null || password.length() == 0) {
			errorList.add("新しいパスワードは必須です");
		}
		else if (password.length() > 20) {
			errorList.add("パスワードは20文字以内です");
		}
		else {
			if(!password.equals(passwordConfirm)) {
			errorList.add("新しいパスワードが一致しませんでした");
			}
		}
		
		//エラーがある場合
		if(errorList.size() > 0 ) {
			model.addAttribute("guest", guest);
			model.addAttribute("errorList", errorList);
			return "settingPassword";
		}
		
		//DB変更
		guest.setPassword(password);
		guest.setOldPassword(oldPassword);
		guest.setUpdateUserId(1);
		guest.setUpdateDate(LocalDateTime.now());
		
		guestRepository.save(guest);
		return "redirect:/user/setting/account";
	}
		
	
	//＝＝退会＝＝
	//DB非表示 →delete_flag
	
	@PostMapping("/user/setting/delete")
	public String delete() {
		//DBデータの取得
		Optional<Guest> guestDbData = guestRepository.findById(guestModel.getId());
		
		if(guestDbData.isEmpty()) {
			return "redirect:/";
		}
		
		// データが取得できた場合、削除  （→ delete_flag = true)
		Guest guest = guestDbData.get();
		guest.setDeleteFlag(true);
		guest.setUpdateUserId(1);
		guest.setUpdateDate(LocalDateTime.now());
		guestRepository.save(guest);
		
		
		return "redirect:/";
	}

//		
//		// 【ユーザ設定画面】
//		// 退会ボタンを押したとき
//		@PostMapping("/user/setting/delete")
//		public String delete() {
//			// 削除対象のデータが存在するか確認
//			Optional<Guest> dbData = guestRepository.findById(user.getId());
//			
//			// データが取得できた場合、削除
//			if(!dbData.isEmpty()) {
//				guestRepository.deleteById(user.getId());
//			}
//			
//			return "login";
//		}
}
