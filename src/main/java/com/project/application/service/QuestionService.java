package com.project.application.service;

import com.project.application.domain.Question;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    List<Question> getAllQuestons();

    void saveQuestion(Question question, String tagName, String email);

    Question getQuestionById(long questionId);

    void updateQuestion(Question question, long questionId);

    void deleteQuestionById(long questionId);

    void addUpVote(long questionId, String email);

    void removeUpVote(long questionId, String email);

    void addDownVote(long questionId, String email);

    void removeDownVote(long questionId, String email);

    Set<Question> getFilteredQuestions(String searchKey, String filterByTags, boolean filterByNoAnswer, boolean noAcceptedAnswer);

    Page<Question> findPaginatedQuestions(int pageNo, int pageSize, String filters, String sortField, String tags, String tagMode);

    Page<Question> getPaginatedSearchRelatedQuestions(int pageNo, int pageSize, String searchKey);

}
