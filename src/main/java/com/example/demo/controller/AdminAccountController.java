package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Guest;
import com.example.demo.model.HostModel;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.GuestRepository;

@Controller
public class AdminAccountController {

	@Autowired
	GuestRepository guestRepository;
	
	@Autowired
	HostModel hostModel;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping("/admin/category")
	public String showCategory(
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			Model model) {

		//ログインしてない時のアクセス不可
		if(hostModel.getId() == null) {
			return "redirect:/admin";
		}
		
		List<Category> categoryList = new ArrayList<>();

		if (keyword != null && keyword.length() > 0) {
			categoryList = categoryRepository.findByName("%" + keyword + "%", Sort.by(Sort.Direction.ASC, "id"));
		} else {
			categoryList = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("categoryList", categoryList);
		return "adminCategory";
	}
	
	@GetMapping("/admin/account")
	public String showAccount(
			@RequestParam(name = "keyword", defaultValue="") String keyword,
			@RequestParam(name = "banFlagNumber", defaultValue="0") Integer banFlagNumber,
			Model model) {

		//ログインしてない時のアクセス不可
		if(hostModel.getId() == null) {
			return "redirect:/admin";
		}
		
		List <Guest> guestList = new ArrayList<>();
//		Boolean banFlag;
//		if(banFlagNumber!=null) {
//			if(banFlagNumber==0) {
//				banFlag = false;
//				if(keyword == null || keyword.length() == 0) {
//					guestList = guestRepository.findByBanFlag(banFlag, Sort.by(Sort.Direction.ASC, "id"));
//				}
////				if(keyword != null && keyword.length() > 0) 
//				else{
//				guestList = guestRepository.findByBanFlagAndName(banFlag, "%" + keyword + "%", Sort.by(Sort.Direction.ASC, "id"));
//				} 
//			}
//			if(banFlagNumber==1) {
//				banFlag = true;
//				if(keyword == null || keyword.length() == 0) {
//					guestList = guestRepository.findByBanFlag(banFlag, Sort.by(Sort.Direction.ASC, "id"));
//				}
//				else{
//				guestList = guestRepository.findByBanFlagAndName(banFlag, "%" + keyword + "%", Sort.by(Sort.Direction.ASC, "id"));
//				} 
//			}
//		}
		
		if(keyword != null && keyword.length() > 0) {
			guestList = guestRepository.findByName("%" + keyword + "%", Sort.by(Sort.Direction.ASC, "id"));
		} 
		
		else {
//		if(banFlagNumber==null){
			guestList = guestRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		}
		
		model.addAttribute("banFlagNumber", banFlagNumber);
		model.addAttribute("keyword", keyword);
		model.addAttribute("guestList", guestList);
		return "adminAccount";
	}

	@PostMapping("/admin/ban/{id}")
	public String ban(
			@PathVariable("id") Integer id
			) {

		Optional<Guest> guestDbData = guestRepository.findById(id);
		if (!guestDbData.isEmpty()) {
			Guest guest = guestDbData.get();
			
			if(guest.getBanFlag()==false) {
				guest.setBanFlag(true);	
			}else {
				guest.setBanFlag(false);
			}
			guest.setUpdateUserId(1);
			guest.setUpdateDate(LocalDateTime.now());
			guestRepository.save(guest);
		}
		
		return "redirect:/admin/account";
	}
}
