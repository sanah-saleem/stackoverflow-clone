package com.project.domain;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="answer")
public class Answer {
	
	@Column(name="id")
	private long id;
	
	@Column(name="content")
	private String content;
	
	@Column(name="is_accepted")
	private boolean isAccepted = false;
	
	@CreationTimestamp
	@Column(name="created_at")
	private Timestamp createdAt; 
	
	@UpdateTimestamp
	@Column(name="updated_at")
	private Timestamp updatedAt;
	
	@ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "question_id")
    private Question question;
	
}
