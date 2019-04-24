package com.hfut.bs.post.rabbitmq;

import com.hfut.bs.common.utils.JsonUtil;
import com.hfut.bs.post.common.EntityTypeEnum;
import com.hfut.bs.post.domain.Comment;
import com.hfut.bs.post.redis.CommentKey;
import com.hfut.bs.post.redis.JedisUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 评论点赞异步化处理
 */
public class CommentLikeHandler  implements MessageListener {

    @Autowired
    JedisUtils jedisUtils;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void onMessage(Message msg) {
        try {
            String str = new String(msg.getBody(),"UTF-8");
            Comment comment = JsonUtil.stringToBean(str, Comment.class);
            // 将点赞数据缓存到redis
            jedisUtils.sadd(CommentKey.LIKE_COMMENT, EntityTypeEnum.getValueByCode(comment.getEntityType())+
                    "_id_"+comment.getEntityType()+"_commentId_"+comment.getEntityId(),comment.getUserId().toString());
            // todo 给评论发布者发送私信

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


