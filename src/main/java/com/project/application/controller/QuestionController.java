package com.project.application.controller;

import com.project.application.domain.Answer;
import com.project.application.domain.Comment;
import com.project.application.domain.Question;
import com.project.application.repository.AuthorRepository;
import com.project.application.service.AnswerService;
import com.project.application.service.AuthorService;
import com.project.application.service.QuestionService;
import com.project.application.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private AuthorService authorService;


    @GetMapping(value={"/","/dashboard"})
    public String home(Model theModel, @RequestParam(value = "filters", required = false) String filters, @RequestParam(value = "sort", defaultValue = "Newest") String sort, @RequestParam(value = "tags", required = false) String tags, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo){

//        List<Question> questions = questionService.getAllQuestons();
//        theModel.addAttribute("questions", questions);

//        if(tags == null){
//            System.out.println("tags null -------------------------------------------------------------------------");
//        }
//        if(tags == ""){
//            System.out.println("tags empty -------------------------------------------------------------------------");
//        }
        int pageSize = 10;
        Page<Question> page = questionService.findPaginatedQuestions(pageNo, pageSize, filters, sort, tags);

        List<Question> questions = page.getContent();

        theModel.addAttribute("currentPage", pageNo);
        theModel.addAttribute("totalPages", page.getTotalPages());
        theModel.addAttribute("totalItems", page.getTotalElements());
        theModel.addAttribute("questions", questions);
        theModel.addAttribute("sortField", sort);
        theModel.addAttribute("filters", filters);
        theModel.addAttribute("tags", tags);
        theModel.addAttribute("from", 1);

        return "dashboard";

//        return findPaginatedResult(theModel, 1, sort);
    }

    @PostMapping("/ask-question")
    public String askQuestion(Model model) {
        model.addAttribute("question", new Question());
        return "input-question";
    }

    @PostMapping("/post-question")
    public String saveQuestion(@ModelAttribute("question") Question question, @RequestParam("tag") String tags){

        questionService.saveQuestion(question, tags, SecurityContextHolder.getContext().getAuthentication().getName());
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
        theModel.addAttribute("author", authorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
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
        answerService.saveAnswer(answer, questionId, SecurityContextHolder.getContext().getAuthentication().getName());
        return getQuestion(theModel, questionId, 0);
    }

    @PostMapping("/update-answer/{id}")
    public String updateAnswer(Model theModel, @PathVariable("id") long answerId,
                                        @RequestParam("questionId") long questionId){
        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("newAnswer", answerService.getAnswerById(answerId));
        answerService.deleteAnswerById(answerId,questionId);
        return "display-question";
    }

    @PostMapping("/delete-answer/{id}")
    public String deleteAnswer(@PathVariable("id") long answerId,
                               @RequestParam("questionId") long questionId, Model model){
        answerService.deleteAnswerById(answerId,questionId);
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
        commentService.saveComment(comment, answerId, SecurityContextHolder.getContext().getAuthentication().getName());
        return getQuestion(theModel, questionId, 0);
    }

    @PostMapping("/delete-comment/{id}")
    public String deleteComment(@PathVariable("id") long commentId, Model theModel,
                                @RequestParam("questionId") long questionId){
        commentService.deleteCommentById(commentId);
        return getQuestion(theModel, questionId, 0);
    }

    @PostMapping("/accept-answer/{id}")
    public String acceptAnswer(@PathVariable("id") long answerId,@RequestParam("questionId") long questionId,Model theModel) {
        answerService.acceptAnswer(answerId, questionId);
        return getQuestion(theModel, questionId, 0);
    }

    @GetMapping("/search")
    public String searchInQuestions(Model theModel,@RequestParam("searchKey") String searchKey, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo){
//        List<Question> searchRelatedQuestions=questionService.getSearchRelatedQuestions(searchKey);
//        model.addAttribute("questions",searchRelatedQuestions);

        int pageSize = 1;
        Page<Question> page = questionService.getPaginatedSearchRelatedQuestions(pageNo, pageSize, searchKey);
        List<Question> searchedQuestions = page.getContent();

        System.out.println(searchedQuestions.size() + "-------------------------------------------------------------------");

        theModel.addAttribute("currentPage", pageNo);
        theModel.addAttribute("totalPages", page.getTotalPages());
        theModel.addAttribute("totalItems", page.getTotalElements());
        theModel.addAttribute("questions", searchedQuestions);
        theModel.addAttribute("searchKey", searchKey);
        theModel.addAttribute("from", 2);

        return "dashboard";
    }

//    @GetMapping("/page/{pageNo}")
//    public String findPaginatedResult(Model theModel, @PathVariable (value = "pageNo") int pageNo, @RequestParam(value = "sortField") String sortField){
//
//        int pageSize = 1;
//        Page<Question> page = questionService.findPaginatedQuestions(pageNo, pageSize, sortField);
//
//        List<Question> questions = page.getContent();
//
//        theModel.addAttribute("currentPage", pageNo);
//        theModel.addAttribute("totalPages", page.getTotalPages());
//        theModel.addAttribute("totalItems", page.getTotalElements());
//        theModel.addAttribute("questions", questions);
//        theModel.addAttribute("sortField", sortField);
//
//        return "dashboard";
//
//    }

    @PostMapping("/upvote")
    public String upvote(@RequestParam("questionId") long questionId, Principal principal, Model theModel) {
        questionService.addUpVote(questionId, principal.getName());

        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("showCommentForId", 0);
        theModel.addAttribute("newAnswer", new Answer());
        theModel.addAttribute("newComment", new Comment());
        theModel.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        theModel.addAttribute("author", authorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "display-question";
    }

    @PostMapping("/remove-upvote")
    public String removeUpvote(@RequestParam("questionId") long questionId, Principal principal, Model theModel) {
        questionService.removeUpVote(questionId, principal.getName());

        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("showCommentForId", 0);
        theModel.addAttribute("newAnswer", new Answer());
        theModel.addAttribute("newComment", new Comment());
        theModel.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        theModel.addAttribute("author", authorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "display-question";
    }

    @PostMapping("/downvote")
    public String downvote(@RequestParam("questionId") long questionId, Principal principal, Model theModel) {
        questionService.addDownVote(questionId, principal.getName());

        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("showCommentForId", 0);
        theModel.addAttribute("newAnswer", new Answer());
        theModel.addAttribute("newComment", new Comment());
        theModel.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        theModel.addAttribute("author", authorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "display-question";
    }

    @PostMapping("/remove-downvote")
    public String removeDownvote(@RequestParam("questionId") long questionId, Principal principal, Model theModel) {
        questionService.removeDownVote(questionId, principal.getName());

        Question question = questionService.getQuestionById(questionId);
        theModel.addAttribute("question", question);
        theModel.addAttribute("showCommentForId", 0);
        theModel.addAttribute("newAnswer", new Answer());
        theModel.addAttribute("newComment", new Comment());
        theModel.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        theModel.addAttribute("author", authorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "display-question";
    }

}
