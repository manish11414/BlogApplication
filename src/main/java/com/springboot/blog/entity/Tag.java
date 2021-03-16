package com.springboot.blog.entity;

import javax.persistence.*;

@Entity
@Table(name = "Tags")
public class Tag {
    @Id
    @GeneratedValue
    @Column(name = "tagId")
    private int tagId;

    @Column(name = "tagName")
    private String tagName;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}