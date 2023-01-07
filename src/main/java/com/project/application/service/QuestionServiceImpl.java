package com.project.application.service;

import com.project.application.domain.Question;
import com.project.application.domain.Tag;
import com.project.application.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TagService tagService;


    @Override
    public List<Question> getAllQuestons(){

        return questionRepository.findAll();
    }
    @Override
    public void saveQuestion(Question question, String tagName){

        System.out.println(tagName);
        List<String> tagNames = Arrays.asList(tagName.split(","));
        System.out.println(tagNames);
        List<Tag> tags = tagService.saveTag(tagNames);
        System.out.println(tags);
        question.setTags(tags);
        questionRepository.save(question);
    }

    @Override
    public Question getQuestionById(long questionId){

        return questionRepository.findById(questionId).get();
    }

    @Override
    public void updateQuestion(Question question, long questionId){

        question.setId(questionId);
        questionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(long questionId){

//        Question question = questionRepository.findById(questionId).get();
        questionRepository.deleteById(questionId);
    }

    @Override
    public List<Question> getSearchedQuestions(String searchKey) {

        List<Question> resultedQuestions = null;
        if (searchKey != null) {

            resultedQuestions.findAllQuestionsWithSearchKey(searchKey);
        }
        return resultedQuestions;
    }


}
