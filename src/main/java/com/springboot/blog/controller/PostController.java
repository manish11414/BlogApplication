package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.PostTag;
import com.springboot.blog.service.PostService;
import com.springboot.blog.service.PostTagService;

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

    private final PostService postService;

    private final PostTagService postTagService;

    public PostController(PostService postService, PostTagService postTagService) {
        this.postService = postService;
        this.postTagService = postTagService;
    }


    @GetMapping("/filtered-post")
    public String filteredPostPage(@ModelAttribute("filterPost") Post filterPost, Model model){

            System.out.println(filterPost.getAuthor());
            System.out.println(filterPost.getTagName());
            System.out.println(filterPost.getPublishedAt());

        String[] authorNameList = filterPost.getAuthor().trim().split(",");
        System.out.println(authorNameList.length);

        if(authorNameList.length == 1) {
            authorNameList = postService.getAllAuthorName();
        }

        String[] tagNameList = filterPost.getTagName().trim().split(",");
        System.out.println(tagNameList.length);

        if(tagNameList.length == 1){
            tagNameList = postService.getAllTagName();
        }

        String[] publishedList = filterPost.getPublishedAt().split(",");

        if(publishedList.length == 1){
            publishedList = postService.getAllPublishedAt();
        }
        else if(publishedList.length == 2){
            publishedList = postService.getAllByPublishedAt(publishedList[1]);
        }
        else{
            String from = publishedList[1];
            String to = publishedList[2];
            int index = 0;

            String[] checkPublishedList = postService.getAllPublishedAt();
            for(String check : checkPublishedList){
                if(from.compareToIgnoreCase(check) >= 0 && to.compareToIgnoreCase(check) <= 0){
                    publishedList[index++] = check;
                }
            }
        }

       model.addAttribute("postFiltered",postService.getFilteredPost(authorNameList, tagNameList, publishedList));

        return "filtered-post";
    }

    @GetMapping("/search")
    public String findByKeyword(@RequestParam("keyword") String keyword, Model model){
        model.addAttribute("searchedPost",postService.getSearchedPosts(keyword));
        model.addAttribute("filteredPost", new Post());
        return "searched-post";
    }

    @RequestMapping("/new-post")
    public String postForm(Model model){
        Post newPost = new Post();
        model.addAttribute("newPost",newPost);
        return "newblog";
    }

    @PostMapping("/addNewPost")
    public String addNewPost(@ModelAttribute("newPost") Post newPost){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = dtf.format(now);
        newPost.setIsPublished(1);
        newPost.setPublishedAt(currentDate);
        newPost.setCreatedAt(currentDate);
        newPost.setUpdatedAt(currentDate);
        postService.addNewPost(newPost);


        String[] tagList = newPost.getTagName().trim().split(",");
        for(String tagName : tagList){
            PostTag newTag = new PostTag();

                newTag.setPostId(newPost.getPostId());
                newTag.setTagName(tagName);
                newTag.setCreatedAt(currentDate);
                newTag.setUpdatedAt(currentDate);

                postTagService.addNewTag(newTag);
        }

        return "index";
    }

    @RequestMapping("/post-list")

    public String showAllPost(Model model){
         return findPaginated(1,"publishedAt", "asc", model);
    }

    @RequestMapping(value = "/selected-post", method = RequestMethod.POST)
    public String updatePostPage(@RequestParam(name="id", required=false) Integer postId, Model model){
        Post updatePost = postService.getPostById(postId);
        model.addAttribute("updatePost", updatePost);
        return "updatepost";
    }

    @RequestMapping(value = "/viewPostPage", method = RequestMethod.POST)
    public String viewPostPage(@RequestParam(name = "id", required = false) Integer postId, Model model){
        Post viewPost = postService.getPostById(postId);
        model.addAttribute("viewPost", viewPost);
        return "post";
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

        Post filterPost = new Post();

        model.addAttribute("currentPageNo",pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("postList", listOfPost);
        model.addAttribute("filterPost",filterPost);

        model.addAttribute("sortField", sortField);
        model.addAttribute("order",order);
        model.addAttribute("reverseOrder", order.equals("asc") ? "desc" : "asc");

        return "postlist";
    }
}
