package com.hfut.bs.course.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DictionaryInfoModel implements Serializable {
        private Integer id;

        private Integer groupCode;

        private String groupName;

        private String key;

        private String value;

        private Integer sort;

        private Date createTime;

        private String createUser;

        private Date updateTime;

        private String updateUser;

        private Boolean del;
}
