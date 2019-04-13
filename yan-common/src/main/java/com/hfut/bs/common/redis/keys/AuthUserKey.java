package com.hfut.bs.common.redis.keys;


import com.hfut.bs.common.redis.BasePrefix;

public class AuthUserKey extends BasePrefix {

    private static final String REDIS_USER_PREFIX = "yan_user_";

    private static final int TOKEN_EXPIRE = 3600 * 24;

    public AuthUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static final AuthUserKey token = new AuthUserKey(TOKEN_EXPIRE, REDIS_USER_PREFIX + "token");
}
