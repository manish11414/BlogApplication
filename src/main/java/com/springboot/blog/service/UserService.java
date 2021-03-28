package com.springboot.blog.service;

import com.springboot.blog.entity.User;
import com.springboot.blog.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
