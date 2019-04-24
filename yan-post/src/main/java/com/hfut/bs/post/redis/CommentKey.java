package com.hfut.bs.post.redis;

import com.hfut.bs.common.redis.BasePrefix;

public class CommentKey extends BasePrefix {

    /**
     * 永不过期
     * @param prefix
     */
    public CommentKey(String prefix) {
        super(prefix);
    }

    public static final CommentKey LIKE_COMMENT = new CommentKey("usersLikeComment_");

//    public static final CommentKey

}
