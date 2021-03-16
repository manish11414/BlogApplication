package com.springboot.blog.entity;

import javax.persistence.*;

@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "commentId")
    private int commentId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "comment")
    private String comment;

    @Column(name = "postId")
    private int postId;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int userId) {
        this.commentId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}
