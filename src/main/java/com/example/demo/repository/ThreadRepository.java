package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Thread;

public interface ThreadRepository extends JpaRepository<Thread, Integer>{

	@Query(value=""
			+"SELECT * "
			+"FROM thread "
			+"WHERE category_id = ?1", nativeQuery = true)
	List<Thread> findByCategoryId(Integer categoryId);
}
