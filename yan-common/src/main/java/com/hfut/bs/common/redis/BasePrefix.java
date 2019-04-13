package com.hfut.bs.common.redis;

/**
 * Created by chenxiong on 18/12/2.
 * 接口(定义契约)->抽象类(实现一些基本的实现)->实现类(实现一些特殊的实现) ＝>涉及模式的模版模式
 */

public abstract class BasePrefix {

    private int expireSeconds;

    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {

        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this(0, prefix);// 默认0代表永不过期
    }


    public int expireSeconds() {
        return expireSeconds;
    }

    /**
     * 可确定获取唯一key
     *
     * @return
     */
    public final String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
