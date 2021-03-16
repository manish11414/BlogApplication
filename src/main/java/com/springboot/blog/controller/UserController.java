package com.springboot.blog.controller;

import com.springboot.blog.entity.User;
import com.springboot.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login-page")
    public String loginPage(){
        return "login";
    }


    @RequestMapping("/register-page")
    public String registerPage(Model model){
        User addUser = new User();
        model.addAttribute("addUser", addUser);
        return "register";
    }

    @PostMapping("/register")
    public String addNewUser(@ModelAttribute("addUser") User newUser){
        userService.saveUser(newUser);
        return "login";
    }
}
