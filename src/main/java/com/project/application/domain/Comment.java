package com.project.application.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Getter
@Setter
@Entity
@Table(name="comment")
public class Comment {

    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name="content")
    private String content;

    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp createdAt;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "answer_id")
    private Answer answer;
}