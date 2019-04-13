package com.hfut.bs.user;

import com.hfut.bs.user.dao.AuthUserMapper;
import com.hfut.bs.user.service.IAuthUserService;
import com.hfut.bs.user.utils.CookieUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        CookieUtil cookieUtil = (CookieUtil) context.getBean("cookieUtil");
        System.in.read();
    }
}
