package com.hfut.bs.post.model;

import lombok.Data;

import java.util.Date;

@Data
public class PostInfoModel {

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

    private int totalLikeCount;

    private int totalCommentCount;

}
