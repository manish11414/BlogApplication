package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.PostTag;
import com.springboot.blog.service.PostService;
import com.springboot.blog.service.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostTagService postTagService;

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


        String[] tagList = newPost.getTagName().trim().split(",");
        for(String tagName : tagList){
            PostTag newTag = new PostTag();

            //if(postTagService.getTagByName(tagName) != null){
                newTag.setPostId(newPost.getPostId());
                newTag.setTagName(tagName);
                newTag.setCreatedAt(currentTime);
                newTag.setUpdatedAt(currentTime);

                postTagService.addNewTag(newTag);
            //}
        }

        return "index";
    }
    @RequestMapping("/post-list")
    //@GetMapping("/index")
    public String showAllPost(Model model){
         return findPaginated(1,"title", "asc", model);
    }

    @RequestMapping(value = "/selected-post", method = RequestMethod.POST)
    public String updatePostPage(@RequestParam(name="id", required=false) Integer postId, Model model){
        Post updatePost = postService.getPostById(postId);
        model.addAttribute("updatePost", updatePost);
        return "updatepost";
    }

    @RequestMapping("/updatePost")
    public String updatePost(@ModelAttribute("updatePost") Post updatePost){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        updatePost.setUpdatedAt(currentTime);
         postService.updatePost(updatePost);
        return "index";
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    public String deletePost(@RequestParam(name="id", required=false) Integer postId){
        postService.deletePost(postId);
        return "index";
    }

    @GetMapping("/http://abc.com/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
         @RequestParam("sortField") String sortField, @RequestParam("order") String order,
                                Model model){

        int pageSize = 10;

        Page<Post> page = postService.findPaginated(pageNo, pageSize, sortField, order);
        List<Post> listOfPost = page.getContent();

        model.addAttribute("currentPageNo",pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("postList", listOfPost);

        model.addAttribute("sortField", sortField);
        model.addAttribute("order",order);
        model.addAttribute("reverseOrder", order.equals("asc") ? "desc" : "asc");

        return "postlist";
    }
}
