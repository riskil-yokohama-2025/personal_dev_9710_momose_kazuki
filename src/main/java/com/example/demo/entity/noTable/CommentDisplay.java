package com.example.demo.entity.noTable;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CommentDisplay {

	//スレッドのID
	@Id
	private Integer id;
	@Column(name = "thread_id")
	private Integer threadId;
	@Column(name = "title")
	private String title;
	@Column(name = "body")
	private String body;
	@Column(name = "comment_body")
	private String commentBody;
	@Column(name = "comment_creator")
	private String commentCreator;
	@Column(name = "comment_creator_id")
	private Integer commentCreatorId;
	@Column(name = "comment_create_date")
	private LocalDateTime commentCreateDate;
	
	
	public CommentDisplay() {
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getCommentBody() {
		return commentBody;
	}
	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}
	public String getCommentCreator() {
		return commentCreator;
	}
	public void setCommentCreator(String commentCreator) {
		this.commentCreator = commentCreator;
	}
	public LocalDateTime getCommentCreateDate() {
		return commentCreateDate;
	}
	public void setCommentCreateDate(LocalDateTime commentCreateDate) {
		this.commentCreateDate = commentCreateDate;
	}
	public Integer getCommentCreatorId() {
		return commentCreatorId;
	}
	public void setCommentCreatorId(Integer commentCreatorId) {
		this.commentCreatorId = commentCreatorId;
	}
	
	
}

