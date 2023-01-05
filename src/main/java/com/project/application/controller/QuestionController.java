package com.project.application.controller;

import com.project.application.domain.Question;
import com.project.application.service.QuestionService;
import com.project.application.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    TagService tagService;

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
        System.out.println(question.getId());
        return "redirect:/";
    }

    @PostMapping("/display-question")
    public String displayQuestion(Model theModel, @RequestParam("questionId") long questionId){
        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("tags", tagService.getAllTags());
        return "display-question";
    }

    @PostMapping("/update-question/{id}")
    public String updateQuestion(@PathVariable("id") long questionId, Model theModel) {
        theModel.addAttribute("question", questionService.getQuestionById(questionId));
        theModel.addAttribute("questionTags", tagService.getAllTags(questionId));
        return "input-question";
    }

    @PostMapping("/delete-question/{id}")
    public String deleteQuestion(@PathVariable("id") long questionId) {
        questionService.deleteQuestionById(questionId);
        return "redirect:/";
    }
}
