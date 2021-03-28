package com.springboot.blog.controller;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/new-comment-page", method = RequestMethod.POST)
    public String newCommentPage(@RequestParam(name="id", required=false) Integer postId, Model model){
        Comment newComment = new Comment();
        newComment.setPostId(postId);
        model.addAttribute("id",postId);
        model.addAttribute("newComment",newComment);
        return "new-comment-page";
    }

    @RequestMapping("/newComment")
    public String addNewComment(@ModelAttribute("newComment") Comment newComment){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        newComment.setCreatedAt(currentTime);
        newComment.setUpdatedAt(currentTime);

        commentService.addNewComment(newComment);
        return "myindex";
    }

    @RequestMapping("/comment-page")
    public String commentPage(@RequestParam(name = "id", required = false) Integer postId, Model model){
        model.addAttribute("commentList", commentService.getCommentsByPostId(postId));
        return "comments-page";
    }

    @RequestMapping(value = "/update-comment-page", method = RequestMethod.POST)
    public String updateCommentPage(@RequestParam(name="id", required=false) Integer commentId, Model model){
        model.addAttribute("updateComment", commentService.getCommentByCommentId(commentId));
        return "update-comment-page";
    }

    @RequestMapping("/updateComment")
    public String updateComment(@ModelAttribute("updateComment") Comment updateComment){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        updateComment.setUpdatedAt(currentTime);
        commentService.updateComment(updateComment);
        return "myindex";
    }

    @RequestMapping(value = "/delete-comment", method = RequestMethod.POST)
    public String deleteComment(@RequestParam(name="id", required=false) Integer commentId){
        commentService.deleteComment(commentId);
        return "myindex";
    }

}
