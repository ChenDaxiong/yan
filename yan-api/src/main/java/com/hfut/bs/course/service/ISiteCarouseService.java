package com.hfut.bs.course.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.model.SiteCarouseInfoModel;

import java.util.List;

public interface ISiteCarouseService {
    /**
     *根据id获取
     **/
    public SiteCarouseInfoModel getById(Integer id);

    /**
     * 获取轮播图
     *
     * @param count 需要获取的数量
     * @return
     */
    public List<SiteCarouseInfoModel> queryCarousels(Integer count);

    /**
     *分页获取
     **/
    public TailPage<SiteCarouseInfoModel> queryPage(SiteCarouseInfoModel queryEntity , TailPage page);

    /**
     *创建
     **/
    public void create(SiteCarouseInfoModel entity);

    /**
     * 创建新记录
     */
    public void createSelectivity(SiteCarouseInfoModel entity);

    /**
     *根据id更新
     **/
    public void update(SiteCarouseInfoModel entity);

    /**
     *根据id 进行可选性更新
     **/
    public void updateSelectivity(SiteCarouseInfoModel entity);

    /**
     *物理删除
     **/
    public void delete(Integer id);

}
