package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.noTable.ThreadDisplay;

public interface ThreadDisplayRepository extends JpaRepository<ThreadDisplay, Integer> {


	//Repository で Queryで書く時は、SELECT文は固定する必要がある！
	//そのためにはじめに以下のように定義しておくと良い
	// 「final」       …以後変更不可 の意味を持つ
	// 「BASE_SELECT」 …定数
	
	public static final String BASE_SELECT = 
			  "select "
			+ "  t.id, "
			+ "  c.id as category_id, "
			+ "  c.name as category_name, "
			+ "  t.title, "
			+ "  t.body,"
			+ "  g.name as creator, "
			+ "  COALESCE(com.create_date, t.create_date) as last_update_date, "
			+ "  t.create_date ";
//
//	SELECT 
//	 t.id,
//	 c.id as category_id,
//	 c.name as category_name,
//	 t.title,
//	 t.body,
//	 g.name as creator,
//	 COALESCE(com.create_date, t.create_date) as last_update_date,
//	 t.create_date 
//	 FROM thread as t 
//	 JOIN category as c ON t.category_id = c.id
//	 JOIN guest as g ON t.user_id = g.id
//	 LEFT OUTER JOIN (
//	    SELECT 
//	        thread_id,
//	        max(create_date) as create_date
//	        FROM comment 
//	        GROUP BY thread_id
//	    ) as com
//	    ON t.id = com.thread_id
//	    WHERE t.delete_flag = false;
	
	
	//情報の検索、リストで管理
	@Query(value = ""
			+ "select "
			+ "  t.id, "                         // 「t. 」とは threadの を示すもの
			                                     //（使用するにあたって FROM 以降で宣言が必要 →-1-）
			+ "  c.id as category_id, "
			+ "  c.name as category_name, "    
			+ "  t.title, "
			+ "  t.body,"
			+ "  g.name as creator, "
			+ "  COALESCE(com.create_date, t.create_date) as last_update_date, "
			+ "  t.create_date "
			+ "from thread as t " //-1- の内容, asは～としての意味で省略可能               
			+ "  INNER JOIN category c on t.category_id =  c.id " //「join」詳しくは下に！
			+ "  INNER JOIN guest g on t.user_id = g.id"
			+ "	 LEFT OUTER JOIN ("
			+ "	    SELECT "
			+ "	        thread_id,"
			+ "	        max(create_date) as create_date"
			+ "	        FROM comment "
			+ "	        GROUP BY thread_id"
			+ "	    ) as com"
			+ "	    ON t.id = com.thread_id"
			+ "	 WHERE t.delete_flag = false", nativeQuery = true) //「category c」「guest g」は as の省略あり
	List<ThreadDisplay> findThreadDisplay();
	
	@Query(value = ""
			+ "select "
			+ "  t.id, "                         // 「t. 」とは threadの を示すもの
			                                     //（使用するにあたって FROM 以降で宣言が必要 →-1-）
			+ "  c.id as category_id, "
			+ "  c.name as category_name, "    
			+ "  t.title, "
			+ "  t.body,"
			+ "  g.name as creator, "
			+ "  COALESCE(com.create_date, t.create_date) as last_update_date, "
			+ "  t.create_date "
			+ "from thread as t " //-1- の内容, asは～としての意味で省略可能               
			+ "  INNER JOIN category c on t.category_id =  c.id " //「join」詳しくは下に！
			+ "  INNER JOIN guest g on t.user_id = g.id"
			+ "	 LEFT OUTER JOIN ("
			+ "	    SELECT "
			+ "	        thread_id,"
			+ "	        max(create_date) as create_date"
			+ "	        FROM comment "
			+ "	        GROUP BY thread_id"
			+ "	    ) as com"
			+ "	    ON t.id = com.thread_id"
			+ "	 WHERE t.delete_flag = false", nativeQuery = true) //「category c」「guest g」は as の省略あり
	List<ThreadDisplay> findThreadDisplay(Sort sort);


	//＝＝「join」＝＝ 
	// 表の結合 ＝ 意味が同じものをつなげる！
	//1. join on で １つの構文になっており、
	//     FROM の後ろが元のテーブル
	//     join の後ろが結合したいテーブル
	//     on   の後ろが 「元のテーブルの要素」「結合したいテーブルの要素」の順で結合を宣言
	//2. join ← inner (存在しない情報は削除)と
	//           outer（存在しない部分は空欄）の2種類が存在する
	
	
	//idによる検索
	@Query(value = ""
			+ BASE_SELECT
			+ "from thread as t " //-1- の内容, asは～としての意味で省略可能               
			+ "  INNER JOIN category c on t.category_id =  c.id " //「join」詳しくは下に！
			+ "  INNER JOIN guest g on t.user_id = g.id"
			+ "	 LEFT OUTER JOIN ("
			+ "	    SELECT "
			+ "	        thread_id,"
			+ "	        max(create_date) as create_date"
			+ "	        FROM comment "
			+ "	        GROUP BY thread_id"
			+ "	    ) as com"
			+ "	    ON t.id = com.thread_id"
			+ "  WHERE t.id = ?1 "
			+ "	    AND t.delete_flag = false", nativeQuery = true) //「category c」「guest g」は as の省略あり
	Optional<ThreadDisplay> findById(Integer id);
	
	//ModelセッションのNameによる検索
	@Query(value = ""
			+ BASE_SELECT
			+ "from thread as t " //-1- の内容, asは～としての意味で省略可能               
			+ "  INNER JOIN category c on t.category_id =  c.id " //「join」詳しくは下に！
			+ "  INNER JOIN guest g on t.user_id = g.id"
			+ "	 LEFT OUTER JOIN ("
			+ "	    SELECT "
			+ "	        thread_id,"
			+ "	        max(create_date) as create_date"
			+ "	        FROM comment "
			+ "	        GROUP BY thread_id"
			+ "	    ) as com"
			+ "	    ON t.id = com.thread_id"
			+ "  WHERE g.name = ?1 "
			+ "	    AND t.delete_flag = false", nativeQuery = true) //「category c」「guest g」は as の省略あり
	List<ThreadDisplay> findByCreator(String creator);
	
//	@Query(value = ""
//			+ BASE_SELECT
//			+ "from thread t "
//			+ "  join category c on c.id = t.category_id "
//			+ "  join guest g on g.id = t.user_id "
//			+ "where c.id = :categoryId ", nativeQuery = true) //静的
//	//			+ "where c.id = ?1 ", nativeQuery = true)                   //動的…今回は存在しないテーブルを参照することもあり、動的に対応していない
//	List<ThreadDisplay> findByCategoryId(@Param("categoryId") Integer categoryId); //「:categoryId 」の「：」の部分と 「アノテーションParam」によって挿入場所の宣言
//	//	List<ThreadDisplay> findByCategoryId(Integer categoryId);
	
	@Query(value = ""
			+ BASE_SELECT
			+ "from thread t "
			+ "  join category c on c.id = t.category_id "
			+ "  join guest g on g.id = t.user_id "
			+ "	 LEFT OUTER JOIN ("
			+ "	    SELECT "
			+ "	        thread_id,"
			+ "	        max(create_date) as create_date"
			+ "	        FROM comment "
			+ "	        GROUP BY thread_id"
			+ "	    ) as com"
			+ "	    ON t.id = com.thread_id"
			+ " WHERE t.delete_flag = false"
			+ "   AND c.id = :categoryId ", nativeQuery = true) //静的
	//			+ "where c.id = ?1 ", nativeQuery = true)                   //動的…今回は存在しないテーブルを参照することもあり、動的に対応していない
	List<ThreadDisplay> findByCategoryId(@Param("categoryId") Integer categoryId); //「:categoryId 」の「：」の部分と 「アノテーションParam」によって挿入場所の宣言
	
	//ソート付
	@Query(value = ""
			+ BASE_SELECT
			+ "from thread t "
			+ "  join category c on c.id = t.category_id "
			+ "  join guest g on g.id = t.user_id "
			+ "	 LEFT OUTER JOIN ("
			+ "	    SELECT "
			+ "	        thread_id,"
			+ "	        max(create_date) as create_date"
			+ "	        FROM comment "
			+ "	        GROUP BY thread_id"
			+ "	    ) as com"
			+ "	    ON t.id = com.thread_id"
			+ " WHERE t.delete_flag = false"
			+ "   AND c.id = :categoryId ", nativeQuery = true) //静的
	//			+ "where c.id = ?1 ", nativeQuery = true)                   //動的…今回は存在しないテーブルを参照することもあり、動的に対応していない
	List<ThreadDisplay> findByCategoryId(@Param("categoryId") Integer categoryId, Sort sort); //「:categoryId 」の「：」の部分と 「アノテーションParam」によって挿入場所の宣言

	@Query(value = ""
			+ BASE_SELECT
			+ "from thread t "
			+ "  join category c on c.id = t.category_id "
			+ "  join guest g on g.id = t.user_id "
			+ "	 LEFT OUTER JOIN ("
			+ "	    SELECT "
			+ "	        thread_id,"
			+ "	        max(create_date) as create_date"
			+ "	        FROM comment "
			+ "	        GROUP BY thread_id"
			+ "	    ) as com"
			+ "	    ON t.id = com.thread_id"
			+ " WHERE t.delete_flag = false"
			+ " order by t.create_date desc", nativeQuery = true)
	List<ThreadDisplay> findAllDesc();

	@Query(value = ""
			+ BASE_SELECT
			+ "from thread t "
			+ "  join category c on c.id = t.category_id "
			+ "  join guest g on g.id = t.user_id "
			+ "	 LEFT OUTER JOIN ("
			+ "	    SELECT "
			+ "	        thread_id,"
			+ "	        max(create_date) as create_date"
			+ "	        FROM comment "
			+ "	        GROUP BY thread_id"
			+ "	    ) as com"
			+ "	    ON t.id = com.thread_id"
			+ " WHERE t.delete_flag = false"
			+ " order by t.create_date asc", nativeQuery = true)
	List<ThreadDisplay> findAllAsc();
}
