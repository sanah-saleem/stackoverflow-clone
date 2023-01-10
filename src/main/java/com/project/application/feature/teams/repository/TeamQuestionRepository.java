package com.project.application.feature.teams.repository;

import com.project.application.domain.TeamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamQuestionRepository extends JpaRepository<TeamQuestion, Long> {
}
