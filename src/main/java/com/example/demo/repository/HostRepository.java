package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Host;

public interface HostRepository extends JpaRepository<Host, Integer>{

	// メールとパスの一致検索
	@Query(value = ""
			+ "SELECT * FROM host "
			+ "WHERE email = ?1 "
			+ "  AND password = ?2", nativeQuery = true)
	Optional<Host> findByEmailAndPassword(String email, String password);
}
