package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.noTable.ThreadDisplay;

public interface ThreadDisplayRepository extends JpaRepository<ThreadDisplay, Integer>{
	
	
	//情報の検索、リストで管理
	@Query(value = ""
			+ "select "
			+ "  t.id, "                         // 「t. 」とは threadの を示すもの
			                                     //（使用するにあたって FROM 以降で宣言が必要 →-1-）
			+ "  c.name as category_name, "    
			+ "  t.title, "
			+ "  c.id as category_id, "
			+ "  g.name as creator, "
			+ "  t.update_date, "
			+ "  t.create_date "
			+ "from thread as t "                 //-1- の内容, asは～としての意味で省略可能               
			+ "  join category c on c.id = t.category_id "       //「join」詳しくは下に！
			+ "  join guest g on g.id = t.user_id", nativeQuery = true)     //「category c」「guest g」は as の省略あり
	List<ThreadDisplay> findThreadDisplay();

	//「join」 
	// 情報の結合 ＝ 意味が同じものをつなげる！
	// join on で １つの構文になっており、
	//   FROM の後ろが元データ
	//   join の後ろが結合したいデータ
	//   on   の後ろが 「結合したいデータの要素」「元データの要素」の順で結合を宣言
	
	
	
	@Query(value = ""
			+ "select "
			+ "  t.id, "
			+ "  c.name as category_name, "
			+ "  t.title, "
			+ "  c.id as category_id, "
			+ "  g.name as creator, "
			+ "  t.update_date, "
			+ "  t.create_date "
			+ "from thread t "
			+ "  join category c on c.id = t.category_id "
			+ "  join guest g on g.id = t.user_id "
			+ "where c.id = :categoryId ", nativeQuery = true)          //静的
//			+ "where c.id = ?1 ", nativeQuery = true)                   //動的…今回は存在しないテーブルを参照することもあり、動的に対応していない
	List<ThreadDisplay> findByCategoryId(@Param("categoryId") Integer categoryId);    //「:categoryId 」の「：」の部分と 「アノテーションParam」によって挿入場所の宣言
//	List<ThreadDisplay> findByCategoryId(Integer categoryId);

	@Query(value = ""
			+ "select "
			+ "  t.id, "
			+ "  c.name as category_name, "
			+ "  t.title, "
			+ "  c.id as category_id, "
			+ "  g.name as creator, "
			+ "  t.update_date, "
			+ "  t.create_date "
			+ "from thread t "
			+ "  join category c on c.id = t.category_id "
			+ "  join guest g on g.id = t.user_id "
			+ "order by t.create_date desc", nativeQuery = true)
	List<ThreadDisplay> findAllDesc();

	@Query(value = ""
			+ "select "
			+ "  t.id, "
			+ "  c.name as category_name, "
			+ "  t.title, "
			+ "  c.id as category_id, "
			+ "  g.name as creator, "
			+ "  t.update_date, "
			+ "  t.create_date "
			+ "from thread t "
			+ "  join category c on c.id = t.category_id "
			+ "  join guest g on g.id = t.user_id "
			+ "order by t.create_date asc", nativeQuery = true)
	List<ThreadDisplay> findAllAsc();
}
