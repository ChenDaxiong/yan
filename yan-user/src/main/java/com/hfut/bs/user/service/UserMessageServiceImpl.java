package com.hfut.bs.user.service;

import com.hfut.bs.user.dao.UserMessageMapper;
import com.hfut.bs.user.domain.UserMessage;
import com.hfut.bs.user.model.UserMessageInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserMessageServiceImpl {

    @Autowired
    UserMessageMapper messageMapper;


    public void addMessage(UserMessageInfoModel messageInfoModel) {
        messageMapper.insert(toUserMessage(messageInfoModel));
    }

    public List<UserMessageInfoModel> getConversationDetail(String conversationId, int offset, int limit) {
        List<UserMessage> messageList = messageMapper.selectByConversationId(conversationId, offset, limit);
        if (messageList == null){
            return null;
        }
        // 使用java8Stream API
        return messageList.stream().map(message->toMessageInfomodel(message)).collect(Collectors.toList());
    }


    public List<UserMessageInfoModel> getConversationList(int userId, int offset, int limit) {
        List<UserMessage> messageList = messageMapper.selectConversationList(userId, offset, limit);
        if (messageList == null){
            return null;
        }
        // 使用java8Stream API
        return messageList.stream().map(message->toMessageInfomodel(message)).collect(Collectors.toList());
    }


    private UserMessage toUserMessage(UserMessageInfoModel messageInfoModel){
        UserMessage message = new UserMessage();
        BeanUtils.copyProperties(messageInfoModel,message);
        return message;
    }

    private UserMessageInfoModel toMessageInfomodel(UserMessage message){
        UserMessageInfoModel messageInfoModel = new UserMessageInfoModel();
        BeanUtils.copyProperties(message,messageInfoModel);
        return messageInfoModel;
    }
}
