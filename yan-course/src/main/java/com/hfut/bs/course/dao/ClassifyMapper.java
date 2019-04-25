package com.hfut.bs.course.dao;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.domain.Classify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Classify record);

    int insertSelective(Classify record);

    Classify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Classify record);

    int updateByPrimaryKey(Classify record);

    List<Classify> selectAll();

    List<Classify> selectByCondition(Classify queryParam);

    int selectTotalItemsCount(Classify queryEntity);

    List<Classify> selectPage(@Param("param") TailPage page);

    int deleteLogic(Integer id);




}