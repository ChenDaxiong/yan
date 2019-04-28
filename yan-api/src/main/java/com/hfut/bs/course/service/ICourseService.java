package com.hfut.bs.course.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.model.CourseInfoModel;
import com.hfut.bs.course.model.CourseQueryParam;

import java.util.List;

/**
 * 课程服务
 *
 */
public interface ICourseService {

    /**
     *根据id获取
     **/
    public CourseInfoModel getById(Integer id);

    /**
     *获取所有
     **/
    public List<CourseInfoModel> queryList(CourseQueryParam queryEntity);

    /**
     *分页获取
     **/
    TailPage<CourseInfoModel> queryPage(CourseInfoModel queryEntity , TailPage<CourseInfoModel> page);

    /**
     *创建
     **/
    public void createSelectivity(CourseInfoModel entity);

    /**
     *根据id 进行可选性更新
     **/
    public void updateSelectivity(CourseInfoModel entity);

    /**
     *物理删除
     **/
    public void delete(Integer id);

    /**
     *逻辑删除
     **/
    public void deleteLogic(Integer id);
}
