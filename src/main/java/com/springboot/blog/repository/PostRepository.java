package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findAllByTagName(String tagName);

    List<Post> findAllByAuthor(String author);

    @Query(value = "select * from posts p where p.title like %:keyword% or p.content like %:keyword% or p.tag_name like %:keyword% o" +
            "r p.author like %:keyword%", nativeQuery = true)
    List<Post> findByKeyword(@Param("keyword") String keyword);

//    @Query(value = "select * from posts p where p.author like %:author% and p.tag_name %:tags% and p.published_at like %:publishDate% ", nativeQuery = true)
//    List<Post> findByFilter(@Param("author") String author, @Param("tags") String tags, @Param("publishDate") String publishDate);


    @Query(value = "select * from posts p where p.author like %:author% and p.tag_name like %:tag_name% and p.published_at like %:published_at% ", nativeQuery = true)
    List<Post> findByAuthorAndTagNameAndPublishedAt(@Param("author") String author, @Param("tag_name") String tag_name, @Param("published_at") String published_at);
}
