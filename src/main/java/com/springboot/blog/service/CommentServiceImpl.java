package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public void addNewComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(int postId) {
       return commentRepository.findAllByPostId(postId);
    }

    @Override
    public Comment getCommentByCommentId(int commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }
}
