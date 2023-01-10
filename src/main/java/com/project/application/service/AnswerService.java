package com.project.application.service;

import com.project.application.domain.Answer;

import java.util.List;

public interface AnswerService {
    void saveAnswer(Answer answer,long questionId, String email);

    void updateAnswer(Answer answer,long answerId);

    Answer getAnswerById(long answerId);

    List<Answer> getAllAnswers();

    void deleteAnswerById(long answerId,long questionId);

    void acceptAnswer(long answerId,long questionId);
}
