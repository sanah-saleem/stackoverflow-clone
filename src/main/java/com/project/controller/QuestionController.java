package com.project.controller;

import com.project.domain.Question;
import com.project.service.QuestionService;
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

    @GetMapping("/")
    public String home(Model theModel){

        List<Question> questions = questionService.getAllQuestons();
        theModel.addAttribute("questions", questions);
        return "home";
    }

    @PostMapping("/post-question")
    public String saveQuestion(@ModelAttribute("question") Question question){

        questionService.saveQuestion(question);

        return "redirect:/";
    }

    @GetMapping("/display-question")
    public String getQuestion(Model theModel, @RequestParam("questionId") long questionId){

        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        return "display-question";
    }

}
