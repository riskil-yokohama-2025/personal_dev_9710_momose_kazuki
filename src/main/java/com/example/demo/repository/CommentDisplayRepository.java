package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.noTable.CommentDisplay;

public interface CommentDisplayRepository extends JpaRepository<CommentDisplay, Integer>{
	
	public static final String BASE_SELECT = 
			  ""
			  + "SELECT "
			  + " com.id,"
			  + " com.thread_id,"
			  + " t.title,"
			  + " t.body,"
			  + " com.body as comment_body,"
			  + " g.name as comment_creator,"
			  + " com.create_date as comment_create_date,"
			  + " com.user_id as comment_creator_id";

	//Notable すべて表示
	@Query(value=""
			+ BASE_SELECT
			+ " FROM comment com "
			+ " LEFT OUTER JOIN thread as t ON com.thread_id = t.id"
			+ " INNER JOIN guest as g ON com.user_id = g.id"
			+ " WHERE com.delete_flag = false;",nativeQuery = true)
	List<CommentDisplay>findCommentDisplay(Sort sort);
	
	//threadIdによる絞り込み
	@Query(value=""
			+ BASE_SELECT
			+ " FROM comment com "
			+ " LEFT OUTER JOIN thread as t ON com.thread_id = t.id"
			+ " INNER JOIN guest as g ON com.user_id = g.id"
			+ " WHERE com.thread_id= ?"
			+ " AND com.delete_flag = false",nativeQuery = true)
	List<CommentDisplay>findCommentDisplayByThreadId(Integer threadId, Sort sort);
}
