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

import com.example.demo.entity.Category;
import com.example.demo.model.HostModel;
import com.example.demo.repository.CategoryRepository;

@Controller
public class AdminCategoryController {
	
	@Autowired
	HostModel hostModel;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	
	@GetMapping("/admin/category/add")
	public String add() {

		//ログインしてない時のアクセス不可
		if(hostModel.getId() == null) {
			return "redirect:/admin";
		}
		
		return "adminCategoryAdd";
	}

	//＝＝カテゴリー追加＝＝
	@PostMapping("/admin/category/add")
	public String store(
			@RequestParam(name = "name", defaultValue = "") String name,
			Model model) {

		List<String> errorList = new ArrayList<>();

		if (name == null || name.length() == 0) {
			errorList.add("カテゴリー名は必須です");
		} else if (name.length() > 20) {
			errorList.add("カテゴリー名は20文字以内にしてください");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "adminCategoryAdd";
		}

		Category category = new Category(name);
		categoryRepository.save(category);
		return "redirect:/admin/category";
	}

	
	//＝＝カテゴリー編集＝＝
	//＝＝カテゴリ変更＝＝
	@GetMapping("/admin/category/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			Model model) {

		//ログインしてない時のアクセス不可
		if(hostModel.getId() == null) {
			return "redirect:/admin";
		}
		
		Optional<Category> categoryDbData = categoryRepository.findById(id);
		
		if (categoryDbData.isEmpty()) {
			return "redirect:/admin/category";
		}
		
		Category category = categoryDbData.get();
		model.addAttribute("category", category);
		return "adminCategoryEdit";
	}

	@PostMapping("/admin/category/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name,
			Model model) {

		Optional<Category> categoryDbData = categoryRepository.findById(id);
		
		if(categoryDbData.isEmpty()) {
			return "redirect:/admin/category";
		}
		
		Category category = categoryDbData.get();
		List<String> errorList = new ArrayList<>();
		
		if (name == null || name.length() == 0) {
			errorList.add("カテゴリー名は必須です");
		} else if (name.length() > 20) {
			errorList.add("カテゴリー名は20文字以内にしてください");
		}
		
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("category", category);
			return "adminCategoryEdit";
		}
		
		category.setName(name);
		category.setUpdateUserId(1);
		category.setUpdateDate(LocalDateTime.now());
		categoryRepository.save(category);
		return "redirect:/admin/category";
	}
}
