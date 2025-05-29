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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Thread;
import com.example.demo.entity.noTable.CommentDisplay;
import com.example.demo.model.GuestModel;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CommentDisplayRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ThreadRepository;

@Controller
public class CommentController {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	ThreadRepository threadRepository;
	
	@Autowired
	CommentDisplayRepository commentDisplayRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	GuestModel guestModel;
	
	
	//==コメント追加画面の表示==
	@GetMapping("/comment/{id}/add")
	public String add(
			@PathVariable(name="id")Integer id,
			Model model) {
		
		//ログインしてない時のアクセス不可
		if(guestModel.getId() == null) {
			return "redirect:/";
		}
		
		
		//データ取得
		Optional<Thread> dbDate = threadRepository.findById(id);
		//データの有無確認
        if(dbDate.isEmpty()) {
        	return "redirect:/thread/" + id + "/detail";
        }
        //オブジェクト生成×セット
        Thread thread = dbDate.get();
		model.addAttribute("thread", thread);
		
		return "commentAdd";
	}
	
	
	//==コメント追加（DB込）==
	@PostMapping("/comment/{id}/add")
	public String store(
			@PathVariable(name="id")Integer id,
			@RequestParam(name="body", defaultValue="") String body,
			Model model) {
		
		//空チェック
		if(body == null || body.length() == 0) {
			return "redirect:/thread/{id}/detail";
		}
		
		//コメントをDBに追加
		Integer threadId = id;
		Integer userId = guestModel.getId();
		Comment comment = new Comment(threadId, userId, body);		
		commentRepository.save(comment);
		
		return "redirect:/thread/" + id + "/detail";
//		return "redirect:/thread/{id}/detail";
	}
	
	
	//==コメント編集==
	//編集画面の表示
	@GetMapping("/comment/{id}/edit")
	public String edit(
			@PathVariable(name="id")Integer id,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		//ログインしてない時のアクセス不可
		if(guestModel.getId() == null) {
			return "redirect:/";
		}
		
		
		//データ取得
		Optional<Comment> commentDbDate = commentRepository.findByIdAndUserId(id, guestModel.getId());
		//データの有無確認
        if(commentDbDate.isEmpty()) {
        	redirectAttributes.addFlashAttribute("errorMessage", "指定されたコメントは存在しません。");
            return "redirect:/thread";
        }
        //オブジェクト生成×セット
        Comment comment = commentDbDate.get();
        
//        //スレッド用
//        Integer threadId = comment.getThreadId();
//        Optional<Thread> threadDbData = threadRepository.findById(threadId);
//        Thread thread = threadDbData.get();
        
		model.addAttribute("comment", comment);
//		model.addAttribute("thread", thread);
		
		return "commentEdit";
	}
	
	//==コメント編集==
	//DB編集
	//＝＝ 更新 ＝＝
	//更新ボタンを押したとき、itemsテーブルにデータを更新する
	// ＊一連の流れ＊
		//1. 更新対象のデータを取得
		//2. 更新予定のデータが存在するか確認
		//3. 取得できなかった場合、更新を行わずに、商品一覧の画面に戻る
				//1.idを入れたオブジェクトを作成する
				//  →DBから取得できたデータの特定のフィールドを書き換える
				//2. 更新を実施
		//4. DBのデータ更新を行う
		//5. 商品一覧の画面を開く(リダイレクト)
	
	@PostMapping("/comment/{id}/edit")
	public String update(
			@PathVariable(name="id")Integer id,
			@RequestParam(name="body", defaultValue="")String body,
			Model model) {
		
		//データ取得
		Optional<Comment> commentDbDate = commentRepository.findById(id);
		
		//データ取得不可チェック
		if(!commentDbDate.isEmpty()) {
			
			Comment comment = commentDbDate.get();
			
			Integer threadId = comment.getThreadId();
			//Integer userId = guestModel.getId();
			
			//空チェック
			if(body == null || body.length() == 0) {
				return "redirect:/thread/" + threadId + "/detail";
			}
			
			//DB更新
			comment.setBody(body);
			comment.setUpdateDate(LocalDateTime.now());
			commentRepository.save(comment);
			
			return "redirect:/thread/" + threadId + "/detail";
		}
		
		//コメントをDBに追加
//		CommentRepository.
		return "redirect:/thread";
	}
	
	//＝＝ 削除 ＝＝
	//削除ボタンを押したとき、テーブルのデータを削除する
	// ＊一連の流れ＊
		//1. 更新対象のデータを取得
		//2. 更新予定のデータが存在するか確認
		//3. 取得できたらデータを削除
		//4. 商品一覧の画面を開く(リダイレクト)

	
	//DB削除 ×
	//DB非表示 →delete_flag
	@PostMapping("/comment/{id}/delete")
	public String delete(
			@PathVariable(name="id")Integer id,
			@RequestParam(name="body", defaultValue="")String body,
			Model model) {
		
		//データ取得
		Optional<Comment> commentDbDate = commentRepository.findById(id);
		
		Integer threadId = commentDbDate.get().getThreadId();
		
		if(!commentDbDate.isEmpty()) {
			//commentRepository.deleteById(id);
			
			Comment comment = commentDbDate.get();
			comment.setDeleteFlag(true);
			comment.setUpdateUserId(1);
			comment.setUpdateDate(LocalDateTime.now());
			commentRepository.save(comment);
		}
		return "redirect:/thread/" + threadId + "/detail";
	}
	
	
	//自分のコメント一覧表示
	@GetMapping("/comment")
	public String index(
			@RequestParam(name="sort",defaultValue = "Desc")String sort,
			Model model) {
		
		//ログインしてない時のアクセス不可
		if(guestModel.getId() == null) {
			return "redirect:/";
		}
		
		Integer guestId = guestModel.getId();
		//List<Comment> commentList = new ArrayList<Comment>();
		//commentList = commentRepository.findByGuestId(guestId);
		List<CommentDisplay> commentList = new ArrayList<CommentDisplay>();
		//commentList = commDisplayRepository.findCommentDisplayByUserId(guestId, Sort.by(Sort.Direction.ASC, "user_Id"));
		
		if(("Asc").equals(sort)) {
			commentList = commentDisplayRepository.findCommentDisplayByUserId(guestId,Sort.by(Sort.Direction.ASC, "comment_create_date"));
		}
		else {
			commentList = commentDisplayRepository.findCommentDisplayByUserId(guestId,Sort.by(Sort.Direction.DESC, "comment_create_date"));
		}
		
		model.addAttribute("commentList", commentList);
		
		
		return "commentMyPage";
	}
}
