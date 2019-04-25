package com.hfut.bs.course.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.model.CollegeInfoModel;

import java.util.List;

public interface ICollegeService {

    /**
     *根据id获取
     **/
    public CollegeInfoModel getById(Integer id);

    /**
     * 根据code获取
     */
    public CollegeInfoModel getByCode(String code);

    /**
     *获取所有
     **/
    public List<CollegeInfoModel> queryAll(CollegeInfoModel queryEntity);

    /**
     *分页获取
     **/
    public TailPage<CollegeInfoModel> queryPage(CollegeInfoModel queryEntity , TailPage page);

    /**
     *创建
     **/
    public void create(CollegeInfoModel entity);

    /**
     * 创建网校
     */
    public void createSelectivity(CollegeInfoModel entity);

    /**
     *根据id更新
     **/
    public void update(CollegeInfoModel entity);

    /**
     *根据id 进行可选性更新
     **/
    public void updateSelectivity(CollegeInfoModel entity);

    /**
     *物理删除
     **/
    public void delete(Integer id);
}
