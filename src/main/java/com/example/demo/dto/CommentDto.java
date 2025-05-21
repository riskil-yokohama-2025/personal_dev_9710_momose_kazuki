package com.example.demo.dto;

public class CommentDto {
	
	private Integer commentId;
	private Integer userId;
	private String name;
	private String body;
	
	public CommentDto() {
	}

	public CommentDto(Integer commentId, Integer userId, String name, String body) {
		this.commentId = commentId;
		this.userId = userId;
		this.name = name;
		this.body = body;
	}



	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	

}
