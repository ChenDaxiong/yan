package com.hfut.bs.course.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CourseInfoModel {

    private Integer id;

    private String name;

    private String type;

    private String classify;

    private String classifyName;

    private String subClassify;

    private String subClassifyName;

    private String direction;

    private String username;

    private Integer level;

    private Integer free;

    private BigDecimal price;

    private String time;

    private Integer onsale;

    private String picture;

    private Integer recommend;

    private Integer weight;

    private Integer studyCount;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer del;

    private String brief;

    public CourseInfoModel(Integer id, String name, String type, String classify, String classifyName, String subClassify, String subClassifyName, String direction, String username, Integer level, Integer free, BigDecimal price, String time, Integer onsale, String picture, Integer recommend, Integer weight, Integer studyCount, Date createTime, String createUser, Date updateTime, String updateUser, Integer del, String brief) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.classify = classify;
        this.classifyName = classifyName;
        this.subClassify = subClassify;
        this.subClassifyName = subClassifyName;
        this.direction = direction;
        this.username = username;
        this.level = level;
        this.free = free;
        this.price = price;
        this.time = time;
        this.onsale = onsale;
        this.picture = picture;
        this.recommend = recommend;
        this.weight = weight;
        this.studyCount = studyCount;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.del = del;
        this.brief = brief;
    }

    public CourseInfoModel() {
        super();
    }
}
