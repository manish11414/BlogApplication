package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.PostTag;
import com.springboot.blog.service.PostService;
import com.springboot.blog.service.PostTagService;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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


//    public String getFilteredPost(@ModelAttribute("filterPost") Post filterPost, Model model){
////        System.out.println(filterPost.getAuthor());
////        System.out.println(filterPost.getTagName());
////        System.out.println(filterPost.getPublishedAt());
//        model.addAttribute("searchedPost", postService.getFilteredPost(filterPost.getAuthor(), filterPost.getTagName(), filterPost.getPublishedAt()));
//        return "searched-post";
//    }

    @GetMapping("/filtered-post")
    public String filteredPostPage(@ModelAttribute("filterPost") Post filterPost, Model model){
       // model.addAttribute("filterPost", postService.getFilteredPost(filterPost.getAuthor(), filterPost.getTagName(), filterPost.getPublishedAt()));

            System.out.println(filterPost.getAuthor());
            System.out.println(filterPost.getTagName());
            System.out.println(filterPost.getPublishedAt());

        String[] authorNameList = filterPost.getAuthor().trim().split(",");

        if(authorNameList.length == 0) {
            authorNameList = postService.getAllAuthorName();
        }

        String[] tagNameList = filterPost.getTagName().trim().split(",");

        if(tagNameList.length == 0){
            tagNameList = postService.getAllTagName();
        }

        String[] publishedList = filterPost.getPublishedAt().split(",");

        if(publishedList.length == 0){
            publishedList = postService.getAllAuthorName();
        }
        else if(publishedList.length == 1){
            publishedList = postService.getAllByPublishedAt(publishedList[0]);
        }


       model.addAttribute("postFiltered",postService.getFilteredPost(authorNameList, tagNameList, publishedList));

//
//        for(String tags : tagNameList){
//            if(tags != null)
//                tagNamePost.addAll(postService.getAllByTagName(tags));
//        }

        //publishedPost.addAll(postService.getAllByPublishedBetween(publishedList[0],publishedList[1]));
 //       publishedPost = postService.getAllByPublishedBetween(publishedList[0],publishedList[1]);
//        for (String publish : publishedList){
//            if(publish != null)
//               publishedPost.add(postService.getAllByPublishedAt(publish));
//        }

           // List<Post> filterPost = authorPost.stream().filter(tagNamePost::contains).filter(publishedPost::contains).collect(Collectors.toList());
//            for(String post: filterPost)
//                System.out.println(post);

        //model.addAttribute("filterPost", authorPost.stream().filter(tagNamePost::contains).filter(publishedPost::contains).collect(Collectors.toList()));
       // model.addAttribute("filterPost",authorPost);
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
         return findPaginated(1,"title", "asc", model);
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
