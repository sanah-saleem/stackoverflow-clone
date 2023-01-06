package com.project.application.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="answer")
public class Answer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	@Column(name="id")
	private long id;
	
	@Column(name="content")
	private String content;
	
	@Column(name="is_accepted")
	private boolean isAccepted = false;
	
	@CreationTimestamp
	@Column(name="created_at", updatable = false)
	private Timestamp createdAt; 
	
	@UpdateTimestamp
	@Column(name="updated_at")
	private Timestamp updatedAt;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "answer_id")
	private List<Comment> comments;

	public void addComment(Comment comment){
		if(comments == null){
			comments = new ArrayList<>();
		}
		comments.add(comment);
	}
	
}
