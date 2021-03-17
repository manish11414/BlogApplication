package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    public void addNewPost(Post newPost){
        postRepository.save(newPost);
    }

    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    public List<Post> getAllPostByTagName(String tagName){
        return postRepository.findAllByTagName(tagName);
    }

    public List<Post> getAllPostByAuthor(String author){
        return postRepository.findAllByAuthor(author);
    }

    public Post getPostById(int postId){
        return postRepository.findById(postId).orElse(null);
    }

    public void updatePost(Post updatePost){
        Post existPost = postRepository.findById(updatePost.getPostId()).orElse(null);
        if(existPost == null)
            return;
        existPost.setTitle(updatePost.getTitle());
        existPost.setAuthor(updatePost.getAuthor());
        existPost.setExcerpt(updatePost.getExcerpt());
        //existPost.setCreatedAt(updatePost.getCreatedAt());
        existPost.setContent(updatePost.getContent());
        //existPost.setPublishedAt(updatePost.getPublishedAt());
        //existPost.setIsPublished(updatePost.getIsPublished());
        existPost.setUpdatedAt(updatePost.getUpdatedAt());
        postRepository.save(existPost);
    }

    public void deletePost(int postId){
        postRepository.deleteById(postId);
    }

}
