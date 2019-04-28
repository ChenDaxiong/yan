package com.hfut.bs.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserCourseSectionInfoModel implements Serializable {

    private Integer id;

    private Integer userId;

    private Integer courseId;

    private Integer sectionId;

    private Integer status;

    private Integer rate;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Boolean del;

    /**
     * 用户名
     */
    private String username;

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 章节名
     */
    private String sectionName;
}
