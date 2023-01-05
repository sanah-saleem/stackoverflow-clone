package com.project.service;

import com.project.domain.Question;
import com.project.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    QuestionRepository questionRepository;


    @Override
    public List<Question> getAllQuestons(){

        return questionRepository.findAll();
    }
    @Override
    public void saveQuestion(Question question){

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

}
