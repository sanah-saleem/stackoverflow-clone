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
    public Page<Question> findAllQuestionsWithNoAcceptedAnswers(Pageable pageable);

    @Query("select q from Question q where (q.id not in (select distinct q.id from Question q join q.answers a ))")
    public Page<Question> findQuestionsWithNoAnswers(Pageable pageable);

    @Query("select q from Question q where (q.id not in (select distinct q.id from Question q join q.answers a )) and (q.id in (select distinct q.id from Question q join q.tags t where t.name in :listTags))")
    public Page<Question> findQuestionsWithNoAnswersWithTags(@Param("listTags") List<String> listTags, Pageable pageable);

    @Query("SELECT distinct q from Question q where (q.hasAcceptedAnswer=false) and (q.id in (select distinct q.id from Question q join q.tags t where t.name in :listTags))")
    public Page<Question> findQuestionsWithNoAcceptedAnswersWithTags(@Param("listTags") List<String> listTags, Pageable pageable);

    @Query("select distinct q from Question q join q.tags t where t.name in :listTags")
    Page<Question> findQuestionsWithTags(@Param("listTags") List<String> listTags, Pageable pageable);
}
