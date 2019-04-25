package com.hfut.bs.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserMessageInfoModel implements Serializable {

    private Integer id;

    private Integer userId;

    private Integer sendUserId;

    private String sendUserName;

    private String refId;

    private String refContent;

    private Boolean type;

    private Boolean status;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Boolean del;

    String conversationId;
}
