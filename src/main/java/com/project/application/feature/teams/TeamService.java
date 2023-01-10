package com.project.application.feature.teams;

import com.project.application.domain.Author;

public interface TeamService {

    public Team saveTeam(String teamName,String email);

    public Team addMember(long teamId,String email);

    public Team removeMember(long teamId,String email);

    public Team makeAdmin(long teamId,String email);
}
