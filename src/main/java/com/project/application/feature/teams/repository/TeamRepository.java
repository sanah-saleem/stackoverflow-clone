package com.project.application.feature.teams.repository;

import com.project.application.feature.teams.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
