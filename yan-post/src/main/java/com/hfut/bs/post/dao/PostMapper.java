package com.hfut.bs.post.dao;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.post.domain.Post;

import java.util.List;

public interface PostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKeyWithBLOBs(Post record);

    int updateByPrimaryKey(Post record);

    List<Post> queryPage(Post record, TailPage<Post> page);


}