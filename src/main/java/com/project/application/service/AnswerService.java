package com.project.application.service;

import com.project.application.domain.Answer;

import java.util.List;

public interface AnswerService {
    void saveAnswer(Answer answer,long questionId);

    void updateAnswer(Answer answer,long answerId);

    Answer getByAnswerId(long answerId);

    List<Answer> getAllAnswers();

    void deleteAnswerById(long answerId);
}
