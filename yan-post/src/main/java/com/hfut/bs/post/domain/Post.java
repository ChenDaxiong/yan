package com.hfut.bs.post.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Post {
    private Integer id;

    private Integer userId;

    private String username;

    private String title;

    private String classifyId;

    private Integer schoolId;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Boolean del;

    private String content;

    public Post(Integer id, Integer userId, String username, String title, String classifyId, Integer schoolId, Date createTime, String createUser, Date updateTime, String updateUser, Boolean del, String content) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.title = title;
        this.classifyId = classifyId;
        this.schoolId = schoolId;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.del = del;
        this.content = content;
    }

    public Post() {
        super();
    }

}