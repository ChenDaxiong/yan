package com.hfut.bs.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserModel  implements Serializable {

    private Integer id;

    private String username;

    private String realname;

    private String password;

    private Boolean gender;

    private String header;

    private String mobile;

    private Boolean status;

    // 学历
    private Date birthday;

    private String education;

    private String collegeCode;

    private String collegeName;

    // 头衔
    private String title;

    private String sign;

    private String openId;

    private String wechatId;

    private String province;

    private String city;

    private String district;
}
