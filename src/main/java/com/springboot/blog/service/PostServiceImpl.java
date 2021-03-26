package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.PostRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void addNewPost(Post newPost){
        postRepository.save(newPost);
    }

    @Override
    public Post getPostById(int postId){
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public String[] getAllAuthorName() {
       return postRepository.findAllAuthorName();
    }

    @Override
    public String[]  getAllTagName() {
        return postRepository.findAllTagName();
    }

    @Override
    public String[] getAllPublishedAt(){
        return postRepository.findAllPublishedAt();
    }

    @Override
    public void updatePost(Post updatePost){
        Post existPost = postRepository.findById(updatePost.getPostId()).orElse(null);
        if(existPost == null)
            return;
       // postRepository.save(updatePost)
        existPost.setTitle(updatePost.getTitle());
        existPost.setAuthor(updatePost.getAuthor());
        existPost.setExcerpt(updatePost.getExcerpt());
        existPost.setTagName(updatePost.getTagName());
        existPost.setContent(updatePost.getContent());
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
    public List<Post> getFilteredPost(String[] author, String[] tag_name, String[] publishedAt) {
        return this.postRepository.findAllByAuthorAndTagNameAndPublishedA(author, tag_name, publishedAt);
    }
}
