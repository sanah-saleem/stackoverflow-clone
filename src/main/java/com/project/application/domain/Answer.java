package com.project.application.domain;

import java.sql.Timestamp;

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
