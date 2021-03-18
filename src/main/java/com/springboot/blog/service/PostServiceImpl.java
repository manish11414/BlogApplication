package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.PostPage;
import com.springboot.blog.entity.PostSearchCriteria;
import com.springboot.blog.repository.PostCriteriaRepository;
import com.springboot.blog.repository.PostRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final PostCriteriaRepository postCriteriaRepository;

    public PostServiceImpl(PostRepository postRepository, PostCriteriaRepository postCriteriaRepository) {
        this.postRepository = postRepository;
        this.postCriteriaRepository = postCriteriaRepository;
    }

    @Override
    public Page<Post> getPosts(PostPage postPage, PostSearchCriteria postSearchCriteria){
        return postCriteriaRepository.findAllWithFilters(postPage, postSearchCriteria);
    }

    @Override
    public void addNewPost(Post newPost){
        postRepository.save(newPost);
    }

    @Override
    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    @Override
    public List<Post> getAllPostByTagName(String tagName){
        return postRepository.findAllByTagName(tagName);
    }

    @Override
    public List<Post> getAllPostByAuthor(String author){
        return postRepository.findAllByAuthor(author);
    }

    @Override
    public Post getPostById(int postId){
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public void updatePost(Post updatePost){
        Post existPost = postRepository.findById(updatePost.getPostId()).orElse(null);
        if(existPost == null)
            return;
        existPost.setTitle(updatePost.getTitle());
        existPost.setAuthor(updatePost.getAuthor());
        existPost.setExcerpt(updatePost.getExcerpt());
        existPost.setTagName(updatePost.getTagName());
        //existPost.setCreatedAt(updatePost.getCreatedAt());
        existPost.setContent(updatePost.getContent());
        //existPost.setPublishedAt(updatePost.getPublishedAt());
        //existPost.setIsPublished(updatePost.getIsPublished());
        existPost.setUpdatedAt(updatePost.getUpdatedAt());
        postRepository.save(existPost);
    }
    @Override
    public void deletePost(int postId){
        postRepository.deleteById(postId);
    }

    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.postRepository.findAll(pageable);
    }

    @Override
    public List<Post> getSearchedPosts(String keyword) {
        return this.postRepository.findByKeyword(keyword);
    }

    @Override
    public List<Post> getFilteredPost(String author, String tags, String publishDate) {
        return postRepository.findByFilter(author, tags, publishDate);
    }


}
