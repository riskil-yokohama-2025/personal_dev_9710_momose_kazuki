package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Thread;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ThreadRepository;

@Controller
public class ThreadController {
	
	@Autowired
	ThreadRepository threadRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	//＝＝掲示板一覧（トップ）＝＝
	@GetMapping("/thread")
	public String index(
			@RequestParam(name="categoryId", defaultValue="") Integer categoryId,
			Model model) {
		
		List<Thread> threadList = new ArrayList<Thread>();
		//threadList = threadRepository.findAll();
		
		//絞り込み
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);
		
		if(categoryId != null) {
			threadList = threadRepository.findByCategoryId(categoryId);
		}
		else {
			threadList = threadRepository.findAll();
		}
		
		model.addAttribute("threadList", threadList);
		return "threadTop";
	}
	
	
	//＝＝掲示板詳細＝＝
	@GetMapping("/thread/{id}/detail")
	public String detail(
			@PathVariable(name="id") Integer id,
			Model model) {
		
		//データ取得
		Optional<Thread> dbDate = threadRepository.findById(id);
		List<Comment> commentDbDate = commentRepository.findByThreadComment(id);
		
		//データの有無確認
        if(dbDate.isEmpty()) {
            return "redirect:/thread";
        }
        //オブジェクト生成×セット
        Thread thread = dbDate.get();
        
		model.addAttribute("thread", thread);
		model.addAttribute("commentDbDate", commentDbDate);
		return "threadDetail";
	}
}
