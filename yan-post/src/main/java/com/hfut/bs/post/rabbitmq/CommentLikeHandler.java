package com.hfut.bs.post.rabbitmq;

import com.hfut.bs.common.utils.JsonUtil;
import com.hfut.bs.post.domain.Comment;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;

public class CommentLikeHandler  implements MessageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void onMessage(Message msg) {
        try {
            String str = new String(msg.getBody(),"UTF-8");
            Comment comment = JsonUtil.stringToBean(str, Comment.class);
            System.out.println("ss");
            //msg就是rabbitmq传来的消息，需要的同学自己打印看一眼
            // 使用jackson解析
            JsonNode jsonData = MAPPER.readTree(msg.getBody());

            System.out.println("我是可爱的熊" + jsonData.get("id").asText()
                    + ",我的名字是");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


