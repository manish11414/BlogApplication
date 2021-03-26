package com.springboot.blog.service;

import com.springboot.blog.dto.UserRegistrationDto;
import com.springboot.blog.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);
}
