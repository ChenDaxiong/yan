package com.hfut.bs.user.dao;

import com.hfut.bs.user.domain.UserMessage;
import org.apache.ibatis.annotations.Param;
import sun.plugin2.message.Conversation;

import java.util.List;

public interface UserMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserMessage record);

    int insertSelective(UserMessage record);

    UserMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserMessage record);

    int updateByPrimaryKey(UserMessage record);

    List<UserMessage> selectByConversationId(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    List<UserMessage> selectConversationList(@Param("userId") Integer userId,@Param("offset")Integer offset,@Param("limit") Integer limit);
}