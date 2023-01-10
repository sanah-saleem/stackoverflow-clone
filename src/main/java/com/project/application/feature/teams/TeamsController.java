package com.project.application.feature.teams;


import com.project.application.domain.TeamQuestion;
import com.project.application.repository.AuthorRepository;
import com.project.application.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class TeamsController {

    @Autowired
    TeamService teamService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("/viewTeams")
    public String viewTeams(Principal principal, Model model) {
        model.addAttribute("teams", authorService.findByEmail(principal.getName()).getTeams());
        return "listTeams";
    }
    @GetMapping("/displayTeam")
    public String displayTeam(Principal principal,Model model,@RequestParam("teamId") long teamId){
        List<TeamQuestion> teamQuestions=teamService.getAllTeamQuestions(teamId);
        model.addAttribute("questions",teamQuestions);
        return "viewTeam";
    }

    @PostMapping("/create-team")
    public String saveTeams (@RequestParam("teamName") String teamName, Principal principal, Model model) {
        Team team = teamService.saveTeam(teamName, principal.getName());
        model.addAttribute("team", team);
        return "viewTeam";
    }

    @PostMapping("/add-member")
    public String addMember(@RequestParam("teamId") long teamId,
                            @RequestParam("memberEmail") String memberEmail, Model model) {
        Team team = teamService.addMember(teamId, memberEmail);
        model.addAttribute("team", team);
        return "viewTeam";
    }

    @PostMapping("/remove-member")
    public String removeMember(@RequestParam("teamId") long teamId,
                               @RequestParam("memberEmail") String memberEmail, Model model) {
        Team team = teamService.removeMember(teamId, memberEmail);
        model.addAttribute("team", team);
        return "viewTeam";
    }

    @PostMapping("/leave-member")
    public String leaveMember(@RequestParam("teamId") long teamId,
                               @RequestParam("memberEmail") String memberEmail, Model model) {
        Team team = teamService.removeMember(teamId, memberEmail);
        model.addAttribute("team", team);
        return "viewTeam";
    }

    @PostMapping("/make-admin")
    public String makeAdmin(@RequestParam("teamId") long teamId,
                            @RequestParam("memberEmail") String memberEmail, Model model) {
        Team team = teamService.makeAdmin(teamId, memberEmail);
        model.addAttribute("team", team);
        return "viewTeam";
    }
}
