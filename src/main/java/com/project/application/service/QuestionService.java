package com.project.application.service;

import com.project.application.domain.Question;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    List<Question> getAllQuestons();

    void saveQuestion(Question question, String tagName, String email);

    Question getQuestionById(long questionId);

    void updateQuestion(Question question, long questionId);

    void deleteQuestionById(long questionId);

    Set<Question> getFilteredQuestions(String filterByTags, boolean filterByNoAnswer, boolean noAcceptedAnswer);

    List<Question> getSearchRelatedQuestions(String searchKey);

}
