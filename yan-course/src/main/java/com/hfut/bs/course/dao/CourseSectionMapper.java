package com.hfut.bs.course.dao;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.domain.CourseSection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseSectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseSection record);

    int insertSelective(CourseSection record);

    CourseSection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseSection record);

    int updateByPrimaryKey(CourseSection record);

    List<CourseSection> selectAll(CourseSection paramEntity);

    int selectMaxSort(Integer courseId);

    int selectTotalItemsCount();

    /**
     *分页获取
     **/
    List<CourseSection> selectPage(@Param("param1") CourseSection queryEntity , @Param("param2") TailPage<CourseSection> page);

    /**
     * 批量创建
     */
    int insertList(List<CourseSection> entityList);

    int deleteLogic(Integer id);

    /**
     * 比当前sort大的，正序排序的第一个
     * @param curCourseSection
     * @return
     */
    public CourseSection selectSortSectionMax(CourseSection curCourseSection);

    /**
     * 比当前sort小的，倒序排序的第一个
     * @param curCourseSection
     * @return
     */
    public CourseSection selectSortSectionMin(CourseSection curCourseSection);


}