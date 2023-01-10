package com.project.application.repository;

import com.project.application.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String tagName);

    @Query("select t from Question q join q.tags t where q.id = :questionId")
    public List<Tag> findAll(long questionId);
}
