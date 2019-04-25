package com.hfut.bs.user.service;

import com.hfut.bs.user.model.UserMessageInfoModel;

import java.util.List;

public interface IUserMessageService {
    void addMessage(UserMessageInfoModel messageInfoModel);

    List<UserMessageInfoModel> getConversationDetail(String conversationId, int offset, int limit);


    List<UserMessageInfoModel> getConversationList(int userId, int offset, int limit);

}
