package com.springboot.blog.repository;

import com.springboot.blog.entity.PostTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagsRepository extends JpaRepository<PostTags, Integer> {
}
