package com.project.application.repository;

import com.project.application.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
