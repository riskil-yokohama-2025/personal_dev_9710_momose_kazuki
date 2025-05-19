package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{


	//過去例
	
//    @Query(value=""
//            +"SELECT * "
//            +"FROM users "
//            +"WHERE name LIKE ?1", nativeQuery = true)
//    List<User> findByKeyword(String keyword);
//
//	@Query(value = ""
//			+ "SELECT * "
//			+ "FROM users "
//			+ "WHERE email = ?1 "
//			+ "  AND password = ?2", nativeQuery = true)
//	List<User> findByEmailAndPassword(String email, String password);

	//"SELECT * FROM comment" 
	//+"WHERE thread_id"

	@Query(value=""
			+"SELECT * FROM comment " 
			+"WHERE thread_id = ?", nativeQuery = true)
	List<Comment> findByThreadComment(Integer id);
}

