package com.hfut.bs.course.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.model.ClassifyInfoModel;

import java.util.List;

public interface IClassifyService {

    /**
     *根据id获取
     **/
    public ClassifyInfoModel getById(Integer id);

    /**
     *获取所有
     **/
    public List<ClassifyInfoModel> queryAll();

    /**
     * 根据code获取
     */
    public ClassifyInfoModel getByCode(String code);

    /**
     *根据条件动态获取
     **/
    public List<ClassifyInfoModel> queryByCondition(ClassifyInfoModel queryEntity);

    /**
     *分页获取
     **/
    public TailPage<ClassifyInfoModel> queryPage(ClassifyInfoModel queryEntity, TailPage page);

    /**
     *创建
     **/
    public void create(ClassifyInfoModel entity);

    /**
     * 创建
     */
    public void createSelectivity(ClassifyInfoModel entity);

    /**
     *根据id 进行可选性更新
     **/
    public void updateSelectivity(ClassifyInfoModel entity);

    /**
     *物理删除
     **/
    public void delete(Integer id);

    /**
     *逻辑删除
     **/
    public void deleteLogic(Integer id);
}
