package com.project.application.repository;

import com.project.application.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String tagName);
}
