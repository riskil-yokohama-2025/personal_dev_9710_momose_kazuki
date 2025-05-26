package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{



	//"SELECT * FROM comment" 
	//+"WHERE thread_id"

	@Query(value=""
			+"SELECT * FROM comment " 
			+"WHERE thread_id = ?", nativeQuery = true)
	List<Comment> findByThreadComment(Integer id);

	@Query(value=""
			+"SELECT * FROM comment " 
			+"WHERE id = ?1"
			+ "  AND user_id = ?2", nativeQuery = true)
	Optional<Comment> findByIdAndUserId(Integer commentId, Integer userId);
	
	
	//ゲストIDによる絞り込み
	@Query(value=""
			+"SELECT * FROM comment " 
			+"WHERE user_id = ?", nativeQuery = true)
	List<Comment> findByGuestId(Integer userId);
	
	@Modifying
	@Transactional
	@Query(value = ""
			+ "UPDATE comment "
			+ "SET delete_flag = true, "
			+ "    update_date = current_timestamp "
			+ "WHERE thread_id = ?1 "
			+ "", nativeQuery = true)
	int deleteByThreadId(Integer threadId);
}

