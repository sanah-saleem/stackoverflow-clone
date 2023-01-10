package com.project.application.feature.teams;

import com.project.application.domain.Author;
import com.project.application.domain.TeamQuestion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    long id;

    @Column(name="name")
    String name;

    @ManyToOne(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name="team_id")
    private Author admin;

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "author_team",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> members;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="team_id")
    private List<TeamQuestion> teamQuestions;

    public void addMember(Author author){
        if(members==null){
            members=new ArrayList<>();
        }
        members.add(author);
    }

    public void removeMember(Author author){
        if(! (members==null)) {
            members.remove(author);
        }
    }
}
