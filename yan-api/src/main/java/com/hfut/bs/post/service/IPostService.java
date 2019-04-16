package com.hfut.bs.post.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.post.model.PostInfoModel;

import java.util.List;

public interface IPostService {

    /**
     * 创建帖子纪录
     * @param postInfoModel
     * @return
     */
    boolean createSelectivity(PostInfoModel postInfoModel);

    /**
     * 获取帖子详情
     *
     * @param id
     * @return
     */
    PostInfoModel getById(Integer id);

    /**
     * 更新帖子内容，注意校验作者
     *
     * @param postInfoModel
     * @return
     */
    boolean updateSelectivity(PostInfoModel postInfoModel);

    /**
     * 获取最热的十个帖子，按照评论数量降序，不分类
     *
     * @return
     */
    List<PostInfoModel> getHotPosts();

    /**
     * 获取某个类别下的帖子，可以指定排序方式＝>热度、时间
     *
     * @param classifyId
     * @param entityQuery
     * @return
     */
     TailPage<PostInfoModel> getPostsByClassified(String classifyId, TailPage entityQuery);





}
