package com.hfut.bs.user.dao;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.user.domain.UserCourseSection;
import com.hfut.bs.user.model.UserCourseSectionInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCourseSectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCourseSection record);

    int insertSelective(UserCourseSection record);

    UserCourseSection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCourseSection record);

    int updateByPrimaryKey(UserCourseSection record);

    /**
     *获取所有
     **/
    public List<UserCourseSection> selectAll(UserCourseSection queryEntity);

    /**
     * 获取最新的学习记录
     */
    public UserCourseSection selectLatest(UserCourseSection queryEntity);

    /**
     *获取总数量
     **/
    public Integer selectTotalItemsCount(UserCourseSection queryEntity);

    /**
     *分页获取
     **/
    public List<UserCourseSection> selectPage(@Param("param1") UserCourseSection queryEntity , @Param("param2") TailPage<UserCourseSectionInfoModel> page);

}