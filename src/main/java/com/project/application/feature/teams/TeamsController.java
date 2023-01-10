package com.project.application.feature.teams;


import com.project.application.domain.TeamQuestion;
import com.project.application.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class TeamsController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("/viewTeams")
    public String viewTeams(Principal principal, Model model) {
        model.addAttribute("teams", authorService.findByEmail(principal.getName()).getTeams());
        return "listTeams";
    }

    @GetMapping("/displayTeam")
    public String displayTeam(Principal principal,Model model,@RequestParam("teamId") long teamId){

        model.addAttribute("team",teamService.getTeamById(teamId));
        return "viewTeam";
    }

    @PostMapping("/create-team")
    public String saveTeams (@RequestParam("teamName") String teamName, Principal principal, Model model) {
        Team team = teamService.saveTeam(teamName, principal.getName());
        model.addAttribute("team", team);
        return "viewTeam";
    }

    @PostMapping("/delete-team")
    public String updateTeams(@RequestParam("teamId") long teamId, Model model, Principal principal) {
        teamService.deleteTeam(teamId);
        model.addAttribute("teams", authorService.findByEmail(principal.getName()).getTeams());
        return "listTeams";
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

    @PostMapping("/ask-team-question")
    public String askTeamQuestion(@RequestParam("teamId") long teamId, Model model) {
        model.addAttribute("teamId", teamId);
        model.addAttribute("newTeamQuestion", new TeamQuestion());
        return "inputTeamQuestion";
    }

    @PostMapping("/save-team-question")
    public String saveTeamQuestion(@RequestParam("teamId") long teamId,
                                   @ModelAttribute("teamQuestion") TeamQuestion teamQuestion, Model model) {
        teamService.saveTeamQuestion(teamId, teamQuestion);
        model.addAttribute("questions", teamService.getTeamById(teamId).getTeamQuestions());
        return "viewTeam";
    }

    @PostMapping("/viewTeamQuestion/{id}")
    public String viewTeamQuestion(@PathVariable("id") long teamQuestionId, Model model) {
        model.addAttribute("teamQuestion", teamService.getTeamQuestionById(teamQuestionId));
        return "viewTeamQuestion";
    }

    @PostMapping("/update-team-question/{id}")
    public String updateTeamQuestion(@PathVariable("id") long teamId,
                                     @RequestParam("teamQuestionId") long teamQuestionId, Model model) {
        model.addAttribute("teamId", teamId);
        model.addAttribute("newTeamQuestion", teamService.getTeamQuestionById(teamQuestionId));
        return "inputTeamQuestion";
    }

    @PostMapping("/delete-team-question/{id}")
    public String deleteTeamQuestion(@PathVariable("id") long teamQuestionId) {
        return "";
    }
}
