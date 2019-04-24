package com.hfut.bs.course.dao;

import com.hfut.bs.course.domain.SiteCarousel;

public interface SiteCarouselMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SiteCarousel record);

    int insertSelective(SiteCarousel record);

    SiteCarousel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SiteCarousel record);

    int updateByPrimaryKey(SiteCarousel record);
}