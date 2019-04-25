package com.hfut.bs.course.dao;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.domain.College;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollegeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(College record);

    int insertSelective(College record);

    College selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(College record);

    int updateByPrimaryKey(College record);

    /**
     * 根据code获取
     */
    public College selectByCode(String code);

    /**
     *获取所有
     **/
    public List<College> selectAll(College queryEntity);

    /**
     *获取总数量
     **/
    public Integer selectTotalItemsCount(College queryEntity);

    /**
     *分页获取
     **/
    public List<College> selectPage(@Param("param1") College queryEntity ,@Param("param2") TailPage page);
}