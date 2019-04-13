package com.hfut.bs.gateway.course;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProviderClient {

    public static void main(String args[]){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("application-provider.xml");
        classPathXmlApplicationContext.start();

        try {
            System.in.read();
        }catch (Exception e){

        }

    }
}
