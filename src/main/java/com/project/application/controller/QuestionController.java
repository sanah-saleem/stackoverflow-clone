package com.project.application.controller;

import com.project.application.domain.Question;
import com.project.application.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping(value={"/","/dashboard"})
    public String home(Model theModel){

        List<Question> questions = questionService.getAllQuestons();
        theModel.addAttribute("questions", questions);
        return "dashboard";
    }

    @GetMapping("/ask-question")
    public String askQuestion(Model model) {
        model.addAttribute(new Question());
        return "input-question";
    }

    @PostMapping("/post-question")
    public String saveQuestion(@ModelAttribute("question") Question question, @RequestParam("tag") String tags){

        questionService.saveQuestion(question, tags);
        return "redirect:/";
    }

    @GetMapping("/display-question")
    public String getQuestion(Model theModel, @RequestParam("questionId") long questionId){

        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        return "display-question";
    }

}
