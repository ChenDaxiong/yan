package com.hfut.bs.gateway.course;

import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public void helloDubbo(String data) {
        System.out.println("hello : " + data);
    }
}
