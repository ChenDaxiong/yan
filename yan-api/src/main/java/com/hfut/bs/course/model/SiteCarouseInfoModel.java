package com.hfut.bs.course.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SiteCarouseInfoModel implements Serializable {

    private Integer id;

    private String name;

    private String picture;

    private String url;

    private Integer weight;

    private Boolean enable;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Boolean del;
}
