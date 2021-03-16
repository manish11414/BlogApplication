package com.springboot.blog.service;

import com.springboot.blog.entity.PostTags;

import java.util.List;

public interface PostTagsService {
    void addPostTags(PostTags postTags);
    List<PostTags> getAllPostTags();
}
