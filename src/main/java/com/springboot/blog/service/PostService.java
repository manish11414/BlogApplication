package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    void addNewPost(Post post);
    List<Post> getAllPost();

    Post getPostById(int postId);

    String[] getAllAuthorName();
    String[] getAllTagName();
    String[] getAllByPublishedAt(String tagName);
    List<Post> getAllByPublishedBetween(String from, String to);

    void updatePost(Post updatePost);
    void deletePost(int postId);
    Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    List<Post> getSearchedPosts(String keyword);


    List<Post> getFilteredPost(String[] author, String[] tags, String[] publishedAt);

}
