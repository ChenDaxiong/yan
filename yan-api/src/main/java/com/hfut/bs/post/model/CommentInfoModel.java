package com.hfut.bs.post.model;

import lombok.Data;

import java.util.Date;

@Data
public class CommentInfoModel {

    private Integer id;

    private Integer userId;

    private String username;

    private Integer entityId;

    private Integer entityType;

    private String content;

    private Integer refId;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Boolean del;

    private Integer refUserId;

    private String refUserName;

    private String refContent;

    private Integer replyCommentCount;



    public CommentInfoModel() {
    }
}
