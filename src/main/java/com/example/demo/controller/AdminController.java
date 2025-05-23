package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.HostModel;

@Controller
public class AdminController {
	
	@Autowired
	HostModel hostModel;
	
	@GetMapping("/admin/home")
	public String index() {

		//ログインしてない時のアクセス不可
		if(hostModel.getId() == null) {
			return "redirect:/admin";
		}
		
		return "adminTop";
	}

}
