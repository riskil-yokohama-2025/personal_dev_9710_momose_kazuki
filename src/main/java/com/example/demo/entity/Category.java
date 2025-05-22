package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//create table public.category (
//		  id serial not null
//		  , name character varying(50) not null
//		  , create_user_id integer default 1 not null
//		  , create_date timestamp(6) without time zone not null
//		  , update_user_id integer
//		  , update_date timestamp(6) without time zone
//		  , delete_flag boolean default false not null
//		  , primary key (id)
//		);

@Entity
@Table(name="category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name")
	private String name;
	
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

	public Category() {
	}
	
	

	public Category(String name) {
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
