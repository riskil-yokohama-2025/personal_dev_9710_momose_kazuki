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
import com.example.demo.entity.Thread;
import com.example.demo.entity.noTable.CommentDisplay;
import com.example.demo.entity.noTable.ThreadDisplay;
import com.example.demo.model.GuestModel;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CommentDisplayRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.ThreadDisplayRepository;
import com.example.demo.repository.ThreadRepository;

@Controller
public class ThreadController {
	
	@Autowired
	ThreadRepository threadRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	GuestRepository guestRepository;
	
	@Autowired
	CommentDisplayRepository commentDisplayRepository;
	
	@Autowired
	ThreadDisplayRepository threadDisplayRepository;
	

	
	@Autowired
	GuestModel guestModel;

	//＝＝掲示板一覧（トップ）＝＝
	@GetMapping("/thread")
	public String index(
			@RequestParam(name="categoryId", defaultValue="") Integer categoryId,
			Model model) {
		
		//ログインしてない時のアクセス不可
		if(guestModel.getId() == null) {
			return "redirect:/";
		}
		
		
		//要素の取得
		//List<Thread> threadList = new ArrayList<Thread>();   //threadDisplayによる管理にしたため不使用へ
		//threadList = threadRepository.findAll();
		List<ThreadDisplay> threadDisplay = new ArrayList<ThreadDisplay>();

		
		//絞り込み
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);
		
		if(categoryId != null) {
			//threadList = threadRepository.findByCategoryId(categoryId);
			threadDisplay = threadDisplayRepository.findByCategoryId(categoryId, Sort.by(Sort.Direction.ASC, "id"));
		}
		else {
			//threadList = threadRepository.findAll();
			threadDisplay = threadDisplayRepository.findThreadDisplay(Sort.by(Sort.Direction.ASC, "id"));
		}
				
		//model.addAttribute("threadList", threadList);
		model.addAttribute("threadDisplay", threadDisplay);
		
		return "threadTop";
	}
	
	
	//＝＝掲示板詳細＝＝
	@GetMapping("/thread/{id}/detail")
	public String detail(
			@PathVariable(name="id") Integer id,
			Model model) {
		
		//ログインしてない時のアクセス不可
		if(guestModel.getId() == null) {
			return "redirect:/";
		}
		
		
		//データ取得
		Optional<ThreadDisplay> dbDate = threadDisplayRepository.findById(id);
		//List<Comment> commentDbDate = commentRepository.findByThreadComment(id);
		List<CommentDisplay> commentList = commentDisplayRepository.findCommentDisplayByThreadId(id, Sort.by(Sort.Direction.ASC, "id"));
		
		//データの有無確認
        if(dbDate.isEmpty()) {
            return "redirect:/thread";
        }
//		//データの有無確認
//        if(commentList.isEmpty()) {
//            return "redirect:/thread";
//        }
        
        ThreadDisplay thread =dbDate.get();
        model.addAttribute("thread", thread);
        model.addAttribute("commentList", commentList);
        
        return "threadDetail";
	}
        
//        //オブジェクト生成×セット
//        Thread thread = dbDate.get();
//		model.addAttribute("thread", thread);
//		
//		String name = guestRepository.serchNameById(thread.getUserId());
//		model.addAttribute("name", name);
//		
//		String categoryName = categoryRepository.serchNameById(thread.getUserId());
//		model.addAttribute("categoryName", categoryName);
//		
//		List<CommentDto> commentDto = new ArrayList<CommentDto>();
//		 //コメントのDBデータ取得後のListを、拡張for文で回して、表示用に準備する
//		for (Comment comment:commentDbDate) {  
//			Integer commentId = comment.getId();
//			Integer userId = comment.getUserId();
//			String nameComment = guestRepository.serchNameById(comment.getUserId());
//			String body = comment.getBody();
//			CommentDto addComment = new CommentDto(commentId, userId, nameComment, body);			
//			commentDto.add(addComment);
//		}
//		model.addAttribute("commentDto", commentDto);
		
		//表示はcommentDtoにお任せするからいらない
		//model.addAttribute("commentDbDate", commentDbDate);



	//＝＝新規スレッド作成＝＝
	//画面表示
	@GetMapping("/thread/add")
	public String add(Model model) {
		
		//ログインしてない時のアクセス不可
		if(guestModel.getId() == null) {
			return "redirect:/";
		}
		
		
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);
		return "threadAdd";
	}
	
	
	//＝＝新規スレッド作成＝＝
	//DB追加
	@PostMapping("/thread/add")
	public String store(
			@RequestParam(name="categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name="title", defaultValue = "") String title,
			@RequestParam(name="body", defaultValue = "") String body,
			Model model) {
		
		//エラーリストの準備
		List<String> errorList = new ArrayList<>();
		
		//エラーチェック
		if (categoryId == null) {
			errorList.add("カテゴリーの選択は必須です");
		}
		
		if (title == null || title.equals("")) {
			errorList.add("タイトルは必須です");
		}
		else if (title.length() > 100) {
			errorList.add("タイトルは100文字以内で入力してください");
		}

		//エラーがある場合
		if(errorList.size() > 0 ) {
			model.addAttribute("errorList", errorList);
			return "threadAdd";
		}
		
		//エラーがない場合
		//登録処理
		Integer guestId = guestModel.getId();
		Thread thread = new Thread(categoryId, guestId, title, body);
		threadRepository.save(thread);
		
		return "redirect:/thread/" + thread.getId() + "/detail";
	}
	
	
	//＝＝作成したスレッドを一覧表示＝＝  
	@GetMapping("/thread/mythread")
	public String mythread(Model model) {
		
		//ログインしてない時のアクセス不可
		if(guestModel.getId() == null) {
			return "redirect:/";
		}
		
		//Integer guestId = guestModel.getId();
		String guestName = guestModel.getName();
		//List<Thread> threadList = new ArrayList<Thread>();
		List<ThreadDisplay> threadList = new ArrayList<ThreadDisplay>();
		threadList = threadDisplayRepository.findByCreator(guestName);
		model.addAttribute("threadList", threadList);
		return "threadMyPage";
	}

	
	//＝＝作成したスレッドを変更＝＝
	//変更画面の表示
	@GetMapping("/thread/{id}/edit")
	public String edit(
			@PathVariable(name="id") Integer id,
			Model model) {
		
		//ログインしてない時のアクセス不可
		if(guestModel.getId() == null) {
			return "redirect:/";
		}
		
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);
		
		//データの取得
		Optional<Thread> threadDbData = threadRepository.findById(id);
		
		
		//データ有無のチェック
		if (threadDbData.isEmpty()) {
			return "redirect:/thread/mythread";
		}
		
		Thread thread = threadDbData.get();
		model.addAttribute("thread", thread);
		return "threadEdit";
	}
	
	
    //＝＝ 更新 ＝＝
    //更新ボタンを押したとき、テーブルのデータを更新する
    // ＊一連の流れ＊
        //1. 更新対象のデータを取得
        //2. 更新予定のデータが存在するか確認
        //3. 取得できなかった場合、更新を行わずに、商品一覧の画面に戻る
                //3.1.idを入れたオブジェクトを作成する
                //  →DBから取得できたデータの特定のフィールドを書き換える
                //3.2. 更新を実施
        //4. DBのデータ更新を行う
        //5. 商品一覧の画面を開く(リダイレクト)
	
	
	//＝＝作成したスレッドを変更＝＝
	//DB変更
	@PostMapping("/thread/{id}/edit")
	public String update(
			@PathVariable(name="id") Integer id,
			@RequestParam(name="categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name="title", defaultValue = "") String title,
			@RequestParam(name="body", defaultValue = "") String body,
			Model model) {
		
		
		//データの取得
		Optional<Thread> threadDbData = threadRepository.findById(id);
		
		//エラーリストの準備
		List<String> errorList = new ArrayList<>();
		
		
		//データ有無のチェック
		if(threadDbData.isEmpty()) {
			return "redirect:/thread/mythread";
		}
		
		
		//エラーチェック
		if (categoryId == null) {
			errorList.add("カテゴリーの選択は必須です");
		}
		
		if (title == null || title.equals("")) {
			errorList.add("タイトルは必須です");
		}
		else if (title.length() > 100) {
			errorList.add("タイトルは100文字以内で入力してください");
		}

		//オブジェクト生成
		Thread thread = threadDbData.get();
		
		//エラーがある場合
		if(errorList.size() > 0 ) {
			model.addAttribute("thread", thread);
			model.addAttribute("errorList", errorList);
			return "threadEdit";
		}
		
		thread.setCategoryId(categoryId);
		thread.setTitle(title);
		thread.setBody(body);
		thread.setUpdateDate(LocalDateTime.now());
		thread.setUpdateUserId(1);
		
		threadRepository.save(thread);
		
		return "redirect:/thread/" + id + "/detail";
	}
	
	
		  //＝＝ 削除 ＝＝
		  //削除ボタンを押したとき、テーブルのデータを削除する
		  // ＊一連の流れ＊
		      //1. 更新対象のデータを取得
		      //2. 更新予定のデータが存在するか確認
		      //3. 取得できたらデータを削除
		      //4. 商品一覧の画面を開く(リダイレクト)
	
	//＝＝作成したスレッドを変更＝＝
	//DB削除 ×
	//DB非表示 →delete_flag
	@PostMapping("/thraed/{id}/delete")
	public String delete(
			@PathVariable(name="id") Integer id) {
		
		//データの取得
		Optional<Thread> threadDbData = threadRepository.findById(id);
		
		//データ有無のチェック →存在したら削除処理
		//DB削除
		if(!threadDbData.isEmpty()) {
			//threadRepository.deleteById(id);
			
			Thread thread = threadDbData.get();
			thread.setDeleteFlag(true);
			thread.setUpdateUserId(1);
			thread.setUpdateDate(LocalDateTime.now());
			threadRepository.save(thread);
		}
		
		return "redirect:/thread/mythread";
	}
}
