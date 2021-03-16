package com.springboot.blog.service;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getUserById(int userId){
        return userRepository.findById(userId).orElse(null);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public void saveUser(User addUser) {
        userRepository.save(addUser);
    }
}
