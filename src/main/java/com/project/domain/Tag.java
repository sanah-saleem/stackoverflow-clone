package com.project.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tag")
public class Tag {
	
	@Column(name="id")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@CreationTimestamp
	@Column(name="created_at")
	private Timestamp createdAt; 
	
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questions;
	
	public void addQuestion(Question question) {
        if (questions == null) {
            questions = new HashSet<>();
        }
        questions.add(question);
    }
	
	public void removeQuestion(Question question) {
		if(questions.contains(question)) {
			questions.remove(question);
		}
	}
}
