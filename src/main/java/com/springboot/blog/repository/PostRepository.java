package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findAllByTagName(String tagName);

    List<Post> findAllByAuthor(String author);

    @Query(value = "select published_at from posts p where p.published_at = :publishedAt", nativeQuery = true)
    String[] findAllByPublishedAt(@Param("publishedAt") String publishedAt);



    @Query(value = "select * from posts p where p.title like %:keyword% or p.content like %:keyword% or p.tag_name like %:keyword% o" +
            "r p.author like %:keyword%", nativeQuery = true)
    List<Post> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "select author from posts", nativeQuery = true)
    String[] findAllByAuthorName();

    @Query(value = "select tag_name from posts", nativeQuery = true)
    String[] findAllByTagName();

//    @Query(value = "select * from posts p where p.author like %:author% and p.tag_name %:tags% and p.published_at like %:publishDate% ", nativeQuery = true)
//    List<Post> findByFilter(@Param("author") String author, @Param("tags") String tags, @Param("publishDate") String publishDate);


   // @Query(value = "select * from posts p where p.author like %:author% and p.tag_name like %:tag_name% and p.published_at like %:published_at% ", nativeQuery = true)
    @Query(value = "select * from posts p where p.tag_name in tag_name and p.author in author ", nativeQuery = true)
    List<Post> findByAuthorAndTagNameAndPublishedAt(@Param("author") String[] author, @Param("tag_name") String[] tag_name);

    @Query(value = "select * from posts p where p.published_at >= from  and  p.published_at <= to", nativeQuery = true)
    List<Post> findAllByPublishedAtBetweenPublishedAt(@Param("from")String from, @Param("to")String to);

    @Query(value = "select * from posts p where p.author IN :authors", nativeQuery = true)
    List<Post> findAllByAuthor(@Param("authors") String[] authors);

    @Query(value = "select * from posts p where p.author IN :authors and p.tag_name IN :tags and p.published_at IN :publishedAt", nativeQuery = true)
    List<Post> findAllByAuthorAndTagNameAndPublishedA(@Param("authors") String[] authors, @Param("tags") String[] tags, @Param("publishedAt") String[] publishedAt);


}

