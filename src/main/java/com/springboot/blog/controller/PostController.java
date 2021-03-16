package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;
//    @Autowired
//    private TagServiceImpl tagService;
//    @Autowired
//    private PostTagsServiceImpl postTagsService;

//    public PostController(PostService postService, TagService tagService, PostTagsService postTagsService) {
//        this.postService = postService;
//        this.tagService = tagService;
//        this.postTagsService = postTagsService;
//    }
    @RequestMapping("/new-post")
    public String postForm(Model model){
        Post newPost = new Post();
        model.addAttribute("newPost",newPost);
        return "newblog";
    }

    @PostMapping("/addNewPost")
    public String addNewPost(@ModelAttribute("newPost") Post newPost){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        newPost.setIsPublished(1);
        newPost.setPublishedAt(currentTime);
        newPost.setCreatedAt(currentTime);
        newPost.setUpdatedAt(currentTime);
        postService.addNewPost(newPost);

//        Tag newTag = new Tag();
//        String[] tagList = newPost.getTagName().trim().split(",");
//        for(String tag : tagList){
//            if(tagServiceImpl.getTagByName(tag) == null){
//                newTag.setTagName(tag);
//                newTag.setCreatedAt(currentTime);
//                newTag.setUpdatedAt(currentTime);
//                tagService.addTag(newTag);
//            }
//        }
//        for(String tag : tagList){
//            PostTags postTags = new PostTags();
//            postTags.setPostId(newPost.getPostId());
//            postTags.setTagId(tagService.getTagByName(tag).getTagId());
//            postTags.setCreatedAt(currentTime);
//            postTags.setUpdatedAt(currentTime);
//            postTagsService.addPostTags(postTags);
//        }
        return "index";
    }
    @RequestMapping("/post-list")
    public String showAllPost(Model model){
        model.addAttribute("postList",postService.getAllPost());
        return "postlist";
    }

    @RequestMapping("/update-post")
    public String updatePost(Model model){
        Post updatePost = new Post();
        model.addAttribute("updatePost", updatePost);
        return "updatepost";
    }
}
