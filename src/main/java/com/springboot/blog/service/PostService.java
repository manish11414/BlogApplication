package com.springboot.blog.service;

import com.springboot.blog.entity.Post;

import java.util.List;

public interface PostService {
    void addNewPost(Post post);
    List<Post> getAllPost();
    Post getPostById(int postId);
    List<Post> getAllPostByTagName(String tagName);
    List<Post> getAllPostByAuthor(String author);
    void updatePost(Post updatePost);
    void deletePost(int postId);
}
