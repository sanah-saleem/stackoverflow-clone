package com.project.application.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	@Column(name="created_at", updatable = false)
	private Timestamp createdAt; 
	
	@UpdateTimestamp
	@Column(name="updated_at")
	private Timestamp updatedAt;

	@Column(name="has_accepted_answer")
	private Boolean hasAcceptedAnswer;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id")
    private List<Answer> answers;
	
	@ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
        name = "question_tag",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="author_id")
	private Author author;
	
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
