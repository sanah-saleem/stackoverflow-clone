package com.project.application.service;

import com.project.application.domain.Comment;

public interface CommentService {


void saveComment(Comment comment,long answerId, String email);


void updateComment(Comment comment,long commentId);


void deleteCommentById(long commentId);


Comment getCommentById(long commentId);


}
