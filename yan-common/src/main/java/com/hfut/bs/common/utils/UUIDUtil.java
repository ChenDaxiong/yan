package com.hfut.bs.common.utils;

import java.util.UUID;

/**
 * Created by chenxiong on 19/1/1.
 */
public class UUIDUtil {

    /**
     * 去掉UUID生成的"－"
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
