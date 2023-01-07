package com.project.application.controller;

import com.project.application.domain.Answer;
import com.project.application.domain.Comment;
import com.project.application.domain.Question;
import com.project.application.service.AnswerService;
import com.project.application.service.QuestionService;
import com.project.application.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public String home(Model theModel, @RequestParam(value = "filters", required = false) String filters, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "tags", required = false) String tags){

        List<Question> questions = questionService.getAllQuestons();
        theModel.addAttribute("questions", questions);

        return "dashboard";
    }

    @PostMapping("/ask-question")
    public String askQuestion(Model model) {
        model.addAttribute("question", new Question());
        return "input-question";
    }

    @PostMapping("/post-question")
    public String saveQuestion(@ModelAttribute("question") Question question, @RequestParam("tag") String tags){

        questionService.saveQuestion(question, tags);
        return "redirect:/";
    }

    @GetMapping("/display-question")
    public String getQuestion(Model theModel,
                              @RequestParam("questionId") long questionId,
                              @RequestParam(value="showCommentForId", required = false) Integer id){
        if (id==null)
        id=0;
        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("showCommentForId", id);
        theModel.addAttribute("newAnswer", new Answer());
        theModel.addAttribute("newComment", new Comment());
        theModel.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return "display-question";
    }

    @PostMapping("/update-question/{id}")
    public String updateQuestion(@PathVariable("id") long questionId, Model theModel) {
        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        return "input-question";
    }

    @PostMapping("/save-answer")
    public String saveAnswer(@ModelAttribute("answer") Answer answer,
                             @RequestParam("questionId") long questionId, Model theModel){
        answerService.saveAnswer(answer, questionId);
        return getQuestion(theModel, questionId, 0);
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
        return getQuestion(model, questionId, 0);
    }


    @PostMapping("/delete-question/{id}")
    public String deleteQuestion(@PathVariable("id") long questionId, Model model){
        questionService.deleteQuestionById(questionId);
        return "redirect:/";
    }

    @PostMapping("/save-comment")
    public String saveComment(@ModelAttribute("comment") Comment comment,
                              @RequestParam("answerId") long answerId, Model theModel,
                              @RequestParam("questionId") long questionId){
        commentService.saveComment(comment, answerId);
        return getQuestion(theModel, questionId, 0);
    }

    @PostMapping("/delete-comment/{id}")
    public String deleteComment(@PathVariable("id") long commentId, Model theModel,
                                @RequestParam("questionId") long questionId){
        commentService.deleteCommentById(commentId);
        return getQuestion(theModel, questionId, 0);
    }

}
