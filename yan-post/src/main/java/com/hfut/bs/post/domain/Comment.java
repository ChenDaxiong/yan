package com.hfut.bs.post.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
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

    public Comment(Integer id, Integer userId, String username, Integer entityId, Integer entityType, String content, Integer refId, Date createTime, String createUser, Date updateTime, String updateUser, Boolean del) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.entityId = entityId;
        this.entityType = entityType;
        this.content = content;
        this.refId = refId;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.del = del;
    }

    public Comment() {
        super();
    }

}