package com.hfut.bs.course.dao;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.domain.Course;
import com.hfut.bs.course.model.CourseInfoModel;
import com.hfut.bs.course.model.CourseQueryParam;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKeyWithBLOBs(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> selectList(CourseQueryParam param);

    int selectTotalItemsCount(Course param);

    List<Course> selectPage(Course queryEntity , TailPage<CourseInfoModel> page);

    int deleteLogic(Integer id);


}