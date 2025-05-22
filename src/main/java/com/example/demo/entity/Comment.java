package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//create table public."comment" (
//		  id serial not null
//		  , thread_id integer
//		  , user_id integer
//		  , body text
//		  , create_user_id integer default 1 not null
//		  , create_date timestamp(6) without time zone not null
//		  , update_user_id integer
//		  , update_date timestamp(6) without time zone
//		  , delete_flag boolean default false not null
//		  , primary key (id)
//		);

@Entity
@Table(name="comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
	@Column(name="thread_id")
	private Integer threadId;	
	@Column(name="user_id")
	private Integer userId;
	@Column(name="body")
	private String body;
	@Column(name="create_user_id")
	private Integer createUserId;
	@Column(name="create_date")
	private LocalDateTime createDate;
	@Column(name="update_user_id")
	private Integer updateUserId;
	@Column(name="update_date")
	private LocalDateTime updateDate;
	@Column(name="delete_flag")
	private Boolean deleteFlag;
	
	public Comment() {
	}
	
	public Comment(Integer threadId, Integer userId, String body, Integer createUserId, LocalDateTime createDate,
			Integer updateUserId, LocalDateTime updateDate, Boolean deleteFlag) {
		this.threadId = threadId;
		this.userId = userId;
		this.body = body;
		this.createUserId = createUserId;
		this.createDate = createDate;
		this.updateUserId = updateUserId;
		this.updateDate = updateDate;
		this.deleteFlag = deleteFlag;
	}

	//新規作成用
	public Comment(Integer threadId, Integer userId, String body) {
		this.threadId = threadId;
		this.userId = userId;
		this.body = body;
		this.createUserId = 1;
		this.createDate = LocalDateTime.now();
		this.updateUserId = 1;
		this.updateDate = LocalDateTime.now();
		this.deleteFlag = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getThreadId() {
		return threadId;
	}

	public void setThreadId(Integer threadId) {
		this.threadId = threadId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	

}
