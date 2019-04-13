package com.hfut.bs.gateway.access;


public class UserContext {

    // 将user对象保存到当前线程中，不会存在线程安全问题
    private static ThreadLocal<Integer> userHolder = new ThreadLocal<Integer>();

    public static void setUserId(Integer userId) {
        userHolder.set(userId);
    }

    public static Integer getUserId() {
        return userHolder.get();
    }
}
