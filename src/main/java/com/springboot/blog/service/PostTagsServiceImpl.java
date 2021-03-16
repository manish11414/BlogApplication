package com.springboot.blog.service;

import com.springboot.blog.entity.PostTags;
import com.springboot.blog.repository.PostTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTagsServiceImpl implements PostTagsService{
    @Autowired
    private PostTagsRepository postTagsRepository;


    public void addPostTags(PostTags newPostTags){
         postTagsRepository.save(newPostTags);
    }

    public List<PostTags> getAllPostTags(){
        return postTagsRepository.findAll();
    }
}
