package com.springboot.blog.service;

import com.springboot.blog.entity.PostTag;
import com.springboot.blog.repository.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostTagServiceImpl implements PostTagService{

    @Autowired
    private PostTagRepository postTagRepository;

    public void addNewTag(PostTag newTag){
        postTagRepository.save(newTag);
    }
    public PostTag getTagByName(String tagName){
        return postTagRepository.findByTagName(tagName);
    }
}
