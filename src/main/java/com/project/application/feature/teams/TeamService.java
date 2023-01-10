package com.project.application.feature.teams;

import com.project.application.domain.Author;
import com.project.application.domain.TeamQuestion;

import java.util.List;

public interface TeamService {

    public Team saveTeam(String teamName,String email);

    void deleteTeam(long teamId);

    public Team addMember(long teamId, String email);

    public Team removeMember(long teamId,String email);

    public Team makeAdmin(long teamId,String email);

    List<TeamQuestion> getAllTeamQuestions(long teamId);

    Team getTeamById(long teamId);

    void saveTeamQuestion(long teamId, TeamQuestion teamQuestion);

    TeamQuestion getTeamQuestionById(long teamQuestionId);
}
