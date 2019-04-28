package com.hfut.bs.user.model;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class UserInfoModel implements Serializable {

    private Integer id;

    private String username;

    private String realname;

    private String password;

    private Boolean gender;

    private String header;

    private String mobile;

    private String salt;

    private Boolean status;

    private Date birthday;

    private String education;

    private String collegeCode;

    private String collegeName;

    private String certNo;

    private String title;

    private String sign;

    private String openId;

    private String wechatId;

    private String qq;

    private Date loginTime;

    private String ip;

    private String province;

    private String city;

    private String district;

    private Integer weight;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Boolean del;

    public Integer getUserId(){
        return id;
    }

    public Set<String> getPermissions(){
        return null;
    }



}
