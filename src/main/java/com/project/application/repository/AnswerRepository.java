package com.project.application.repository;

import com.project.application.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface AnswerRepository extends JpaRepository<Answer,Long> {

}
