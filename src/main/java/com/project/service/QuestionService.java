package com.project.service;

import com.project.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestons();

    void saveQuestion(Question question);

    Question getQuestionById(long questionId);

    void updateQuestion(Question question, long questionId);

    void deleteQuestionById(long questionId);
}
