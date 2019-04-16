package com.hfut.bs.post;

import com.hfut.bs.post.domain.Comment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.HashMap;

public class PostProvider {

    public static void main( String[] args ) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        RabbitTemplate rabbitTemplate = (RabbitTemplate) context.getBean("rabbitTemplate");
        Comment comment = new Comment();
        comment.setContent("LALLAL");
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("id", "1");
//        //根据key发送到对应的队列
        rabbitTemplate.convertAndSend("que_cat_key", comment);
        System.out.println("lalla");

    }

}
