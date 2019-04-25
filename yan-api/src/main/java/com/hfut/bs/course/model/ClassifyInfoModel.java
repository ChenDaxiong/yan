package com.hfut.bs.course.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ClassifyInfoModel implements Serializable {
    private Integer id;

    private String name;

    private String code;

    private String parentCode;

    private Integer sort;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Boolean del;
}
