package com.springboot.blog.service;

import com.springboot.blog.entity.PostTag;

public interface PostTagService {
    void addNewTag(PostTag newTag);
    PostTag getTagByName(String tagName);
}
