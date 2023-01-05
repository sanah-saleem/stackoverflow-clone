package com.project.application.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="question")
public class Question {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	@Column(name="id")
	private long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="problem")
	private String problem;

	@Column(name="expectation")
	private String expectation;
	
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
    private List<Tag> tags;
	
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
			tags = new ArrayList<>();
		}
		tags.add(tag);
	}
	
	public void removeTag(Tag tag) {
		if(tags.contains(tag)) {
			tags.remove(tag);
		}
	}
}
