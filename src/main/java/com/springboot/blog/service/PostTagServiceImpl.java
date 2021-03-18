package com.springboot.blog.service;

import com.springboot.blog.entity.PostTag;
import com.springboot.blog.repository.PostTagRepository;

import org.springframework.stereotype.Service;

@Service
public class PostTagServiceImpl implements PostTagService{

    private final PostTagRepository postTagRepository;

    public PostTagServiceImpl(PostTagRepository postTagRepository) {
        this.postTagRepository = postTagRepository;
    }

    public void addNewTag(PostTag newTag){
        postTagRepository.save(newTag);
    }
    public PostTag getTagByName(String tagName){
        return postTagRepository.findByTagName(tagName);
    }
}
