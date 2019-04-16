package com.hfut.bs.post.dao;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.post.domain.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectComments(@Param("entityId") int entityId, @Param("entityType") int entityType,
    @Param("queryParam") TailPage queryParam);

    Integer selectTotalCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    /**
     * 获取某条评论下的所有回复评论
     *
     * @param entityId
     * @param entityType
     * @param commentId
     * @param queryParam
     * @return
     */
    List<Comment> selectReplyComments(@Param("entityId") int entityId, @Param("entityType") int entityType,
                                      @Param("commentId")Integer commentId ,@Param("queryParam") TailPage queryParam);

    Integer selectCommentReplyCount(@Param("entityId") int entityId, @Param("entityType") int entityType,
                                    @Param("commentId") Integer commentId);
}