package com.hfut.bs.user.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.model.CourseSectionInfoModel;
import com.hfut.bs.user.model.UserCourseSectionInfoModel;

import java.util.List;

public interface IUserCourseSectionService {

    /**
     *根据id获取
     **/
    public UserCourseSectionInfoModel getById(Integer id);

    /**
     *获取所有
     **/
    public List<UserCourseSectionInfoModel> queryAll(UserCourseSectionInfoModel queryEntity);

    /**
     * 获取最新的
     */
    public UserCourseSectionInfoModel queryLatest(UserCourseSectionInfoModel queryEntity);

    /**
     *分页获取
     **/
    public TailPage<UserCourseSectionInfoModel> queryPage(UserCourseSectionInfoModel queryEntity , TailPage<UserCourseSectionInfoModel> page);

    /**
     *创建
     **/
    public void createSelectivity(UserCourseSectionInfoModel entity);

    /**
     *根据id更新
     **/
    public void update(UserCourseSectionInfoModel entity);

    /**
     *根据id 进行可选性更新
     **/
    public void updateSelectivity(UserCourseSectionInfoModel entity);

    /**
     *物理删除
     **/
    public void delete(Integer id);

}
