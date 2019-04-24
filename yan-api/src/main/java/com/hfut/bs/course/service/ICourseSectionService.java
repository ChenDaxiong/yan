package com.hfut.bs.course.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.model.CourseSectionInfoModel;

import java.util.List;

public interface ICourseSectionService {

    /**
     *根据id获取
     **/
    public CourseSectionInfoModel getById(Integer id);

    /**
     *获取所有
     **/
    public List<CourseSectionInfoModel> queryAll(CourseSectionInfoModel queryEntity);

    /**
     * 获取课程章最大的sort
     */
    public Integer getMaxSort(Integer courseId);

    /**
     *分页获取
     **/
    public TailPage<CourseSectionInfoModel> queryPage(CourseSectionInfoModel queryEntity , TailPage page);

    /**
     *创建
     **/
    public void createSelectivity(CourseSectionInfoModel entity);

    /**
     *批量创建
     **/
    public void createList(List<CourseSectionInfoModel> entityList);

    /**
     *根据id更新
     **/
    public void update(CourseSectionInfoModel entity);

    /**
     *根据id 进行可选性更新
     **/
    public void updateSelectivity(CourseSectionInfoModel entity);

    /**
     *物理删除
     **/
    public void delete(Integer id);

    /**
     *逻辑删除
     **/
    public void deleteLogic(Integer id);

    /**
     * 比当前sort大的，正序排序的第一个
     * @param curCourseSection
     * @return
     */
    public CourseSectionInfoModel getSortSectionMax(CourseSectionInfoModel curCourseSection);

    /**
     * 比当前sort小的，倒序排序的第一个
     * @param curCourseSection
     * @return
     */
    public CourseSectionInfoModel getSortSectionMin(CourseSectionInfoModel curCourseSection);
}
