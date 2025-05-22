package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

//create table public.guest (
//		  id serial not null
//		  , email character varying(128) not null
//		  , password character varying(50) not null
//		  , old_password character varying(50)
//		  , name character varying(50) not null
//		  , ban_flag boolean default false
//		  , create_user_id integer default 1 not null
//		  , create_date timestamp(6) without time zone not null
//		  , update_user_id integer
//		  , update_date timestamp(6) without time zone
//		  , delete_flag boolean default false not null
//		  , primary key (id)
//		);

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="guest")
public class Guest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
	@Column(name="email")
	private String email;	
	@Column(name="password")
	private String password;
	@Column(name="old_password")
	private String oldPassword;
	@Column(name="name")
	private String name;
	@Column(name="ban_flag")
	private Boolean banFlag;
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
	
	public Guest() {
	}

	
//	public Guest(String email, String password, String oldPassword, String name, Boolean banFlag, Integer createUserId,
//			LocalDateTime createDate, Integer updateUserId, LocalDateTime updateDate, Boolean deleteFlag) {
//		this.email = email;
//		this.password = password;
//		this.oldPassword = oldPassword;
//		this.name = name;
//		this.banFlag = banFlag;
//		this.createUserId = createUserId;
//		this.createDate = createDate;
//		this.updateUserId = updateUserId;
//		this.updateDate = updateDate;
//		this.deleteFlag = deleteFlag;
//	}

	//ログイン用
	public Guest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	

	//新規登録用
	public Guest(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.banFlag = false;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getBanFlag() {
		return banFlag;
	}

	public void setBanFlag(Boolean banFlag) {
		this.banFlag = banFlag;
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
