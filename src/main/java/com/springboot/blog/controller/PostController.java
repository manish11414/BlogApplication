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
import java.util.ArrayList;
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
    public String filteredPostPage(@ModelAttribute("filterPost") Post filterPost, Model model) {

        System.out.println(filterPost.getAuthor());
        System.out.println(filterPost.getTagName());
        System.out.println(filterPost.getPublishedAt());

        String[] authorNameList = filterPost.getAuthor().split(",");
        System.out.println(authorNameList.length);


        if (authorNameList[0].equalsIgnoreCase("")) {
            authorNameList = postService.getAllAuthorName();
        }

        String[] tagNameList = filterPost.getTagName().split(",");

        if (tagNameList[0].equalsIgnoreCase("")) {
            tagNameList = postService.getAllTagName();
        }


        String[] publishedList = filterPost.getPublishedAt().split(",");

        if (publishedList[0].equalsIgnoreCase("")) {
            publishedList = postService.getAllPublishedAt();
        } else if (publishedList.length == 2) {
            String from = publishedList[0];
            String to = publishedList[1];
            int index = 0;

            List<String> publishedAtList = new ArrayList<>();

            String[] checkPublishedList = postService.getAllPublishedAt();
            for (String check : checkPublishedList) {
                if (check.compareToIgnoreCase(from) >= 0 && check.compareToIgnoreCase(to) <= 0) {
                    publishedAtList.add(check);
                }
            }
            publishedList = new String[publishedAtList.size() + 1];
            for (String list : publishedAtList) {
                publishedList[index++] = list;
            }
        }

        model.addAttribute("postFiltered", postService.getFilteredPost(authorNameList, tagNameList, publishedList));

        return "filtered-post";
    }

    @GetMapping("/search")
    public String findByKeyword(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("searchedPost", postService.getSearchedPosts(keyword));
        model.addAttribute("filteredPost", new Post());
        return "searched-post";
    }

    @GetMapping("/new-post")
    public String postForm(Model model) {
        Post post = new Post();
        model.addAttribute("newPost", post);
        return "create-new-blog-page";
    }

    @PostMapping("/addNewPost")
    public String addNewPost(@ModelAttribute("newPost") Post post) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = dtf.format(now);
        System.out.println(post.getEmail());
        post.setIsPublished(1);
        post.setPublishedAt(currentDate);
        post.setCreatedAt(currentDate);
        post.setUpdatedAt(currentDate);
        postService.addNewPost(post);


        String[] tagList = post.getTagName().trim().split(",");
        for (String tagName : tagList) {
            PostTag tag = new PostTag();

            tag.setPostId(post.getPostId());
            tag.setTagName(tagName);
            tag.setCreatedAt(currentDate);
            tag.setUpdatedAt(currentDate);

            postTagService.addNewTag(tag);
        }
        return "index";
    }

    @GetMapping("/post-list")

    public String showAllPost(Model model) {
        return findPaginated(1, "publishedAt", "asc", model);
    }

    @RequestMapping(value = "/selected-post", method = RequestMethod.POST)
    public String updatePostPage(@RequestParam(name = "id", required = false) Integer postId, Model model) {
        Post updatePost = postService.getPostById(postId);
        model.addAttribute("updatePost", updatePost);
        return "update-post-page";
    }

    @RequestMapping(value = "/viewPostPage", method = RequestMethod.POST)
    public String viewPostPage(@RequestParam(name = "id", required = false) Integer postId, Model model) {
        Post viewPost = postService.getPostById(postId);
        model.addAttribute("viewPost", viewPost);
        return "selected-post-page";
    }

    @RequestMapping("/updatePost")
    public String updatePost(@ModelAttribute("updatePost") Post updatePost) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        updatePost.setUpdatedAt(currentTime);
        postService.updatePost(updatePost);
        return "index";
    }


    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    public String deletePost(@RequestParam(name = "id", required = false) Integer postId) {
        postService.deletePost(postId);
        return "index";
    }

    @GetMapping("/http:/abc.com/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField, @RequestParam("order") String order,
                                Model model) {

        int pageSize = 10;

        Page<Post> page = postService.findPaginated(pageNo, pageSize, sortField, order);
        List<Post> listOfPost = page.getContent();

        Post filterPost = new Post();

        model.addAttribute("currentPageNo", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("postList", listOfPost);
        model.addAttribute("filterPost", filterPost);

        model.addAttribute("sortField", sortField);
        model.addAttribute("order", order);
        model.addAttribute("reverseOrder", order.equals("asc") ? "desc" : "asc");

        return "post-list";
    }
}
