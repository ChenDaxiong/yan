package com.hfut.bs.course.model;

import lombok.Data;

import java.util.Date;

@Data
public class CourseSectionInfoModel {
    private Integer id;

    private Integer courseId;

    private Integer parentId;

    private String name;

    private Integer sort;

    private String time;

    private Boolean onsale;

    private String videoUrl;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Boolean del;
}
