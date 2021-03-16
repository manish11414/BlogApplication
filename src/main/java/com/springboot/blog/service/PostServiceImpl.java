package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    public void addNewPost(Post newPost){
        postRepository.save(newPost);
    }

    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    public List<Post> getAllPostByTagName(String tagName){
        return postRepository.findAllByTagName(tagName);
    }

    public List<Post> getAllPostByAuthor(String author){
        return postRepository.findAllByAuthor(author);
    }

}
