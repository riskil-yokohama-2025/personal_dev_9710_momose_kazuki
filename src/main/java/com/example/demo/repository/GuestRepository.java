package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer>{

	// メールとパスの一致検索
	@Query(value = ""
			+ "SELECT * FROM guest "
			+ "WHERE email = ?1 "
			+ "  AND password = ?2", nativeQuery = true)
	Optional<Guest> findByEmailAndPassword(String email, String password);

}
