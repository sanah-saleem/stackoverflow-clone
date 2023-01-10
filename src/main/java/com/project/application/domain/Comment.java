package com.project.application.domain;

import java.sql.Timestamp;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


@Getter
@Setter
@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name="content")
    private String content;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private Timestamp createdAt;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="author_id")
    private Author author;

//    @ManyToOne(cascade = {
//            CascadeType.DETACH, CascadeType.MERGE,
//            CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumn(name = "answer_id")
//    private Answer answer;
}