package com.project.application.feature.teams;


import com.project.application.domain.Answer;
import com.project.application.domain.TeamQuestion;
import com.project.application.service.AnswerService;
import com.project.application.service.AuthorService;
import com.project.application.service.TagService;
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

    @Autowired
    TagService tagService;

    @Autowired
    AnswerService answerService;

    @GetMapping("/viewTeams")
    public String viewTeams(Principal principal, Model model) {
        model.addAttribute("teams", authorService.findByEmail(principal.getName()).getTeams());
        return "listTeams";
    }

    @GetMapping("/displayTeam")
    public String displayTeam(Principal principal,Model model,
                              @RequestParam("teamId") long teamId){

        model.addAttribute("team",teamService.getTeamById(teamId));
        model.addAttribute("username", principal.getName());
        return "viewTeam";
    }

    @PostMapping("/create-team")
    public String saveTeams (@RequestParam("teamName") String teamName, Principal principal, Model model) {
        Team team = teamService.saveTeam(teamName, principal.getName());
        model.addAttribute("team", team);
        model.addAttribute("username", principal.getName());
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
                            @RequestParam("memberEmail") String memberEmail, Model model,
                            Principal principal) {
        Team team = teamService.addMember(teamId, memberEmail);
        model.addAttribute("team", team);
        model.addAttribute("username", principal.getName());
        return "viewTeam";
    }

    @PostMapping("/remove-member")
    public String removeMember(@RequestParam("teamId") long teamId,
                               @RequestParam("memberEmail") String memberEmail,
                               Model model, Principal principal) {
        Team team = teamService.removeMember(teamId, memberEmail);
        model.addAttribute("team", team);
        model.addAttribute("username", principal.getName());
        return "viewTeam";
    }

    @PostMapping("/leave-member")
    public String leaveMember(@RequestParam("teamId") long teamId,
                               @RequestParam("memberEmail") String memberEmail,
                              Model model, Principal principal) {
        Team team = teamService.removeMember(teamId, memberEmail);
        model.addAttribute("team", team);
        model.addAttribute("username", principal.getName());
        return "viewTeam";
    }

    @PostMapping("/make-admin")
    public String makeAdmin(@RequestParam("teamId") long teamId,
                            @RequestParam("memberEmail") String memberEmail,
                            Model model, Principal principal) {
        Team team = teamService.makeAdmin(teamId, memberEmail);
        model.addAttribute("team", team);
        model.addAttribute("username", principal.getName());
        return "viewTeam";
    }

    @PostMapping("/ask-team-question")
    public String askTeamQuestion(@RequestParam("teamId") long teamId, Model model) {
        model.addAttribute("teamId", teamId);
        model.addAttribute("question", new TeamQuestion());
        return "inputTeamQuestion";
    }

    @PostMapping("/save-team-question")
    public String saveTeamQuestion(@RequestParam("teamId") long teamId,
                                   @RequestParam("tag") String tagNames,
                                   @ModelAttribute("question") TeamQuestion teamQuestion,
                                   Model model, Principal principal) {
        teamService.saveTeamQuestion(teamId, teamQuestion, tagNames);
        model.addAttribute("team", teamService.getTeamById(teamId));
        model.addAttribute("username", principal.getName());
        return "viewTeam";
    }

    @GetMapping("/viewTeamQuestion")
    public String viewTeamQuestion(@RequestParam("questionId") long teamQuestionId, Model model) {
        model.addAttribute("question", teamService.getTeamQuestionById(teamQuestionId));
        model.addAttribute("newAnswer", new Answer());
        return "viewTeamQuestion";
    }

//    @PostMapping("/update-team-question/{id}")
//    public String updateTeamQuestion(@PathVariable("id") long teamQuestionId,
//                                     @RequestParam("teamId") long teamId, Model model) {
//        model.addAttribute("teamId", teamId);
//        model.addAttribute("question", teamService.getTeamQuestionById(teamQuestionId));
//        model.addAttribute("questionTags", );
//        return "inputTeamQuestion";
//    }

//    @PostMapping("/delete-team-question/{id}")
//    public String deleteTeamQuestion(@PathVariable("id") long teamQuestionId,
//                                     @RequestParam("teamId") long teamId,
//                                     Model model, Principal principal) {
//        teamService.deleteTeam(teamQuestionId);
//        model.addAttribute("team", teamService.getTeamById(teamId));
//        model.addAttribute("username", principal.getName());
//        return "viewTeam";
//    }

    @PostMapping("/save-team-answer")
    public String saveTeamAnswer(@RequestParam("questionId") long questionId,
                                 @ModelAttribute("newAnswer") Answer answer,
                                 Model model) {
        teamService.saveTeamAnswer(questionId, answer);
        model.addAttribute("question", teamService.getTeamQuestionById(questionId));
        model.addAttribute("newAnswer", new Answer());
        return "viewTeamQuestion";
    }
}
