package com.project.application.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", insertable=false, updatable = false)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String authorRole = "ROLE_USER";

    @ManyToMany(targetEntity=Tag.class, fetch=FetchType.EAGER, cascade =
            {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
            @JoinTable(
            name="author_tag",
            joinColumns=@JoinColumn(name="author_id"),
            inverseJoinColumns=@JoinColumn(name="tag_id"))
    private List<Tag> tagsWatched;

    public void addTagWatched(Tag tag) {
        if(tagsWatched == null) {
            tagsWatched = new ArrayList<>();
        }
        tagsWatched.add(tag);
    }

    public void deleteTagWatched(Tag tag) {
        tagsWatched.remove(tag);
    }
}
