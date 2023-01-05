package com.project.application.controller;

import com.project.application.domain.Answer;
import com.project.application.domain.Question;
import com.project.application.service.AnswerService;
import com.project.application.service.QuestionService;
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
    AnswerService answerService;

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

        List<Answer> answers = answerService.getAllAnswers();
        theModel.addAttribute("answers", answers);

        return "display-question";
    }

    @PostMapping("/#")
    public String saveAnswer(@ModelAttribute("answer") Answer answer, long answerId){

        answerService.saveAnswer(answer, answerId);
        return "";
    }

    @PutMapping("/#")
    public String updateAnswer(Model theModel, @RequestParam("answerId") long answerId){

        theModel.addAttribute("answer", answerService.getByAnswerId(answerId));
        return "";
    }

    @DeleteMapping("/#")
    public String deleteAnswer(@RequestParam("answerId") long answerId){

        answerService.deleteAnswerById(answerId);
        return "";
    }

}
