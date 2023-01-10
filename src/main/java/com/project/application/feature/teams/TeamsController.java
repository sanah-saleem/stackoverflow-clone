package com.project.application.feature.teams;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class TeamsController {

    @Autowired
    TeamService teamService;

    @PostMapping("/create-team")
    public String saveTeams (@RequestParam("teamName") String teamName, Principal principal, Model model) {
        Team team = teamService.saveTeam(teamName, principal.getName());
        model.addAttribute("team", team);
        return "#";
    }

    @PostMapping("/add-member")
    public String addMember(@RequestParam("teamId") long teamId,
                            @RequestParam("teamId") long memberEmail, Model model) {
        Team team = teamService.saveTeam(teamId, memberEmail);
        model.addAttribute("team", team);
        return "#";
    }

    @PostMapping("/remove-member")
    public String removeMember(@RequestParam("teamId") long teamId,
                               @RequestParam("teamId") long memberEmail, Model model) {
        Team team = teamService.removeMember(teamId, memberEmail);
        model.addAttribute("team", team);
        return "#";
    }

    @PostMapping("/make-admin")
    public String makeAdmin(@RequestParam("teamId") long teamId,
                            @RequestParam("teamId") long memberEmail, Model model) {
        Team team = teamService.makeAdmin(teamId, memberEmail);
        model.addAttribute("team", team);
        return "#";
    }
}
