package com.project.application.feature.teams;

import com.project.application.domain.Author;

public interface TeamService {

    public void saveTeam(Team team);

    public void addMember(Author author);

    public void removeMember(Author author);

    public void makeAdmin(Author author);
}
