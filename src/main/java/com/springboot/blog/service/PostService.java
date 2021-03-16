package com.springboot.blog.service;

import com.springboot.blog.entity.Post;

import java.util.List;

public interface PostService {
    void addNewPost(Post post);
    List<Post> getAllPost();
    List<Post> getAllPostByTagName(String tagName);
    List<Post> getAllPostByAuthor(String author);
}
