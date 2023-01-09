package com.project.application.repository;

import com.project.application.domain.Question;
import com.project.application.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT distinct Q from Question Q join Q.tags T where (Q.title like %:search%) or (Q.problem like %:search%) or (T.name like %:search%)")
    public Page<Question> findAllQuestionsWithSearchKey(@Param("search") String searchKey, Pageable pageable);



//@Query("SELECT distinct Q from Question Q where (Q.id NOT IN :questionIdsWithAcceptedAnswers)")
//    public List<Question> findAllQuestionWithNoAcceptedAnswers(@Param("questionIdsWithAcceptedAnswers") List<Long> questionIdsWithAcceptedAnswers);

    //queries for filtering

    @Query("SELECT distinct Q from Question Q where Q.hasAcceptedAnswer=false")
    public List<Question> findAllQuestionsWithNoAcceptedAnswers(Pageable pageable);

    @Query("select q from Question q where (q.id not in (select distinct q.id from Question q join Answer a ))")
    public List<Question> findQuestionsWithNoAnswers(@Param("listTags") List<Tag> listTags, Pageable pageable);

    @Query("select q from Question q where (q.id not in (select distinct q.id from Question q join Answer a )) and (q.id in (select distinct q.id from Question q join q.tags t where t.name in :listTags))")
    public List<Question> findQuestionsWithNoAnswersWithTags(@Param("listTags") List<Tag> listTags, Pageable pageable);

    @Query("SELECT distinct q from Question q where (q.hasAcceptedAnswer=false) and (q.id in (select distinct q.id from Question q join q.tags t where t.name in :listTags))")
    public List<Question> findQuestionsWithNoAcceptedAnswersWithTags(@Param("listTags") List<Tag> listTags, Pageable pageabl);
}
