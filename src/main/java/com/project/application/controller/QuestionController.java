package com.project.application.controller;

import com.project.application.domain.Answer;
import com.project.application.domain.Comment;
import com.project.application.domain.Question;
import com.project.application.service.AnswerService;
import com.project.application.service.QuestionService;
import com.project.application.service.CommentService;
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

    @Autowired
    CommentService commentService;

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
        theModel.addAttribute("newAnswer", new Answer());
        return "display-question";
    }

    @PostMapping("/save-answer")
    public String saveAnswer(@ModelAttribute("answer") Answer answer,
                             @RequestParam("questionId") long questionId, Model theModel){
        answerService.saveAnswer(answer, questionId);
        return getQuestion(theModel, questionId);
    }

    @PostMapping("/update-answer/{id}")
    public String updateAnswer(Model theModel, @PathVariable("id") long answerId,
                                        @RequestParam("questionId") long questionId){
        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("newAnswer", answerService.getAnswerById(answerId));
        answerService.deleteAnswerById(answerId);
        return "display-question";
    }

    @PostMapping("/delete-answer/{id}")
    public String deleteAnswer(@PathVariable("id") long answerId,
                               @RequestParam("questionId") long questionId, Model model){
        answerService.deleteAnswerById(answerId);
        return getQuestion(model, questionId);
    }

    @PostMapping("/delete-question/{id}")
    public String deleteQuestion(@PathVariable("id") long questionId, Model model){
        questionService.deleteQuestionById(questionId);
        return "redirect:/";
    }

    @PostMapping("/#comment")
    public String saveComment(@ModelAttribute("comment") Comment comment, @RequestParam("answerId") long answerId){

        commentService.saveComment(comment, answerId);
        return "";
    }

    @PostMapping("/#commentupdate")
    public String updateComment(Model theModel, @RequestParam("commentId") long commentId){

        theModel.addAttribute("answer", commentService.getCommentById(commentId));
        return "";
    }

    @PostMapping("/#commentdelete")
    public String deleteComment(@RequestParam("commentId") long commentId){

        commentService.deleteCommentById(commentId);
        return "";
    }

}
