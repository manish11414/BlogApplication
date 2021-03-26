package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query(value = "select author from posts p ", nativeQuery = true)
    String[] findAllAuthorName();

    @Query(value = "select tag_name from posts p ", nativeQuery = true)
    String[] findAllTagName();

    @Query(value  ="select published_at from posts p ", nativeQuery = true)
    String[] findAllPublishedAt();


    @Query(value = "select * from posts p where p.title like %:keyword% or p.content like %:keyword% or p.tag_name like %:keyword% o" +
            "r p.author like %:keyword%", nativeQuery = true)
    List<Post> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "select * from posts p where p.author IN :authors and p.tag_name IN :tags and p.published_at IN :publishedAt", nativeQuery = true)
    List<Post> findAllByAuthorAndTagNameAndPublishedA(@Param("authors") String[] authors, @Param("tags") String[] tags, @Param("publishedAt") String[] publishedAt);
}

