package com.hfut.bs.course.dao;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.domain.SiteCarousel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SiteCarouselMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SiteCarousel record);

    int insertSelective(SiteCarousel record);

    SiteCarousel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SiteCarousel record);

    int updateByPrimaryKey(SiteCarousel record);

    List<SiteCarousel> selectCarousels(@Param("count") Integer count);

    int selectTotalItemsCount();

    List<SiteCarousel> selectPage(@Param("param")TailPage page);
}

