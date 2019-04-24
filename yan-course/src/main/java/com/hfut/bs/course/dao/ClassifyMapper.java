package com.hfut.bs.course.dao;

import com.hfut.bs.course.domain.Classify;

public interface ClassifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Classify record);

    int insertSelective(Classify record);

    Classify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Classify record);

    int updateByPrimaryKey(Classify record);
}