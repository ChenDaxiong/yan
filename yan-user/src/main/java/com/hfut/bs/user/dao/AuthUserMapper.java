package com.hfut.bs.user.dao;

import com.hfut.bs.user.domain.AuthUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthUser record);

    int insertSelective(AuthUser record);

    AuthUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthUser record);

    int updateByPrimaryKey(AuthUser record);

    /**
     * 根据username获取
     */
    AuthUser getByUsername(@Param("username") String username);

    AuthUser getByUsernameAndPassword(@Param("username") String username,@Param("password")String password);

    List<AuthUser> getHomepageTeachers();

    int deleteLogic(@Param("id") int id);
}