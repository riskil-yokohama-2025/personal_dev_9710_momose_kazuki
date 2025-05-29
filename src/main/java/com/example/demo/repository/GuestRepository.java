package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
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

	
	// id によるゲスト名の検索
	@Query(value="SELECT name "
			+ "FROM guest "
			+ "WHERE id = ?1", nativeQuery = true)
	String serchNameById(Integer userId);
	
	
	// id によるパスワードの検索
	@Query(value="SELECT password "
			+ "FROM guest "
			+ "WHERE id = ?1", nativeQuery = true)
	String findPasswordById(Integer id);
	
	//名前による検索
	@Query(value="SELECT * "
			+ "FROM guest "
			+ "WHERE name LIKE ?1", nativeQuery = true)
	List <Guest> findByName(String keyword, Sort sort);
	
	//emailによる検索
	@Query(value = ""
			+ "SELECT * FROM guest "
			+ "WHERE email = ?1 ", nativeQuery = true)
	Optional<Guest> findByEmail(String email);
	
	//banFlagによる検索
	@Query(value = ""
			+ "SELECT * FROM guest "
			+ "WHERE ban_flag = ?1 ", nativeQuery = true)
	List<Guest> findByBanFlag(Boolean banFlag, Sort sort);
	
	//banFlag・名前による検索
	@Query(value = ""
			+ "SELECT * FROM guest "
			+ "WHERE ban_flag = ?1 "
			+ "  AND name LIKE ?2", nativeQuery = true)
	List<Guest> findByBanFlagAndName(Boolean banFlag, String keyword, Sort sort);
	

}
