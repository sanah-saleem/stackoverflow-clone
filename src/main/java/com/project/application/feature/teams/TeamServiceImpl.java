package com.project.application.feature.teams;

import com.project.application.domain.Author;
import com.project.application.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;

public class TeamServiceImpl implements TeamService{
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    AuthorService authorService;


    @Override
    public Team saveTeam(String teamName,String email) {
        Author author=authorService.findByEmail(email);
        Team team=new Team();
        team.setName(teamName);
        team.setAdmin(author);
        team.addMember(author);
        teamRepository.save(team);
        return team;
    }

    @Override
    public Team addMember(long teamId,String email) {
        Team team=teamRepository.findById(teamId).get();
        Author author=authorService.findByEmail(email);
        if(author==null){
            return team;
        }
        if(team.members.contains(author)){
            return team;
        }
        team.addMember(author);
        teamRepository.save(team);
        return team;
    }

    @Override
    public Team removeMember(long teamId,String email) {
        Team team=teamRepository.findById(teamId).get();
        Author author= authorService.findByEmail(email);
        if(author==null){
            return team;
        }
        if(!team.members.contains(author)){
            return team;
        }
        team.members.remove(author);
        teamRepository.save(team);
        return team;
    }

    @Override
    public Team makeAdmin(long teamId,String email) {
        Team team=teamRepository.findById(teamId).get();
        Author author= authorService.findByEmail(email);
        team.setAdmin(author);
        return team;
    }
}
