package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	@Query(value="SELECT name "
			+ "FROM category "
			+ "WHERE id = ?1", nativeQuery = true)
	String serchNameById(Integer categoryId);
}
