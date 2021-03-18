package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.repository.CommentRepository;

import java.util.List;

public interface CommentService {

    void addNewComment(Comment comment);
    List<Comment> getCommentsByPostId(int postId);
    Comment getCommentByCommentId(int commentId);
    void deleteComment(int commentId);
    void updateComment(Comment comment);


}
