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

import com.example.demo.entity.Host;
import com.example.demo.model.HostModel;
import com.example.demo.repository.HostRepository;

@Controller
public class AdminLoginController {

	@Autowired
	HttpSession session;
	
	@Autowired
	HostRepository hostRepository;
	
	@Autowired
	HostModel hostModel;
	
	@GetMapping({ "/admin", "/admin/login", "/admin/logout" })
	public String index(
			@RequestParam(name = "error", defaultValue = "") String error,
			Model model) {

		if(error.equals("notLoggedIn")) {
			List<String> errorList = new ArrayList<>();
			errorList.add("ログインしてください");
			model.addAttribute("errorList", errorList);
		}
		
		session.invalidate();
		return "adminLogin";
	}
	
	
	//ログイン処理
	@PostMapping("/admin/login")
	public String login(
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {

		//DBデータ確認
		Optional<Host> hostDbData = hostRepository.findByEmailAndPassword(email, password);
		List<String> errorList = new ArrayList<>();

		if (email == null || email.length() == 0) {
			errorList.add("メールアドレスは必須です");
		} 

		if (password == null || password.length() == 0) {
			errorList.add("パスワードは必須です");
		} 
		
		if(email.length() > 0 && password.length() > 0) {
			if(hostDbData.isEmpty()) {	
				errorList.add( "メールアドレスとパスワードが一致しませんでした");
			}
			else {
				Host host = hostDbData.get();
				hostModel.setId(host.getId());
				hostModel.setName(host.getName());
			}
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "adminLogin";
		}
		
		return "redirect:/admin/home";
	}
}
