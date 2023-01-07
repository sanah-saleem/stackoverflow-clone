package com.project.application.repository;

import com.project.application.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByEmail(String email);
}
