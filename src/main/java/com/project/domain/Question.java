package com.project.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="question")
public class Question {
	
	@Column(name="id")
	private long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="body")
	private String body;
	
	@CreationTimestamp
	@Column(name="created_at")
	private Timestamp createdAt; 
	
	@UpdateTimestamp
	@Column(name="updated_at")
	private Timestamp updatedAt;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;
	
	@ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
        name = "question_tag",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
	
	public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answer);
    }
	
	public void removeAnswer(Answer answer) {
		if(answers.contains(answer)) {
			answers.remove(answer);
		}
	}
	
	public void addTag(Tag tag) {
		if(tags == null) {
			tags = new HashSet<>();
		}
		tags.add(tag);
	}
	
	public void removeTag(Tag tag) {
		if(tags.contains(tag)) {
			tags.remove(tag);
		}
	}
}
