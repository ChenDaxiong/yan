package com.hfut.bs.gateway;

import com.hfut.bs.gateway.course.DemoService;
import com.hfut.bs.gateway.redis.JedisUtils;
import com.hfut.bs.gateway.user.AuthController;
import com.hfut.bs.user.model.UserModel;
import com.hfut.bs.user.service.IAuthUserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class ConsumerClient {



    public static void main(String args[]){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        context.start();

        JedisUtils jedisUtils = (JedisUtils) context.getBean("jedisUtils");

        IAuthUserService authUserService = (IAuthUserService) context.getBean("authUserService");
        AuthController authController =(AuthController)context.getBean("authController");
        UserModel userModel = authUserService.getByUsername("q");
        System.out.println("a");

        while (true){
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.next();

            //  获取接口
            DemoService demoService = (DemoService) context.getBean("la");
            demoService.helloDubbo(msg);
        }
    }
}
