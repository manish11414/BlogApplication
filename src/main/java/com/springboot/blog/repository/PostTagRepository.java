package com.springboot.blog.repository;

import com.springboot.blog.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag,Integer> {
    PostTag findByTagName(String tagName);
}
