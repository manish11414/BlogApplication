package com.springboot.blog.entity;

import jdk.jfr.DataAmount;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @GeneratedValue
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
