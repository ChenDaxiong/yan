package com.hfut.bs.user.utils;

import com.hfut.bs.common.redis.keys.AuthUserKey;
import com.hfut.bs.user.domain.AuthUser;
import com.hfut.bs.user.redis.JedisUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenxiong
 */
@Service
public class CookieUtil {

    private final static Logger log = LoggerFactory.getLogger(CookieUtil.class);

    private final static String COOKIE_DOMAIN = ".happymmall.com";
    public final static String COOKIE_NAME = "miaosha_login_token";

    @Autowired
    private JedisUtils jedisUtils;


    /**
     * 根据客户端请求携带的cookie获取user对象，并更新cookie过期时间。
     *
     * @param request
     * @param response
     * @return
     */
    public AuthUser readLoginToken(HttpServletRequest request, HttpServletResponse response) {
        String oldToken = getLoginToken(request);
        AuthUser user = getUserByToken(oldToken, response);
        return user;
    }

    //X:domain=".happymmall.com"
    //a:A.happymmall.com            cookie:domain=A.happymmall.com;path="/"
    //b:B.happymmall.com            cookie:domain=B.happymmall.com;path="/"
    //c:A.happymmall.com/test/cc    cookie:domain=A.happymmall.com;path="/test/cc"
    //d:A.happymmall.com/test/dd    cookie:domain=A.happymmall.com;path="/test/dd"
    //e:A.happymmall.com/test       cookie:domain=A.happymmall.com;path="/test"

    public void writeLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
//        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");//代表设置在根目录
        ck.setHttpOnly(true);
        //单位是秒。
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        ck.setMaxAge(60 * 60 * 24 * 365);//如果是-1，代表永久
        log.info("write cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
    }


    public void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
//                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//设置成0，代表删除此cookie。
                    log.info("del cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

    /**
     * 根据token从缓存中取出user对象并更新Cookie的过期时间
     *
     * @param token
     * @return
     */
    public AuthUser getUserByToken(String token, HttpServletResponse response) {
        AuthUser user = jedisUtils.get(AuthUserKey.token, token, AuthUser.class);
        // 更新Cookie的过期时间
        updateToken(user, token, response);
        return user;
    }

    /**
     * 从客户端的HttpServletRequest请求中获取到用户登陆的cookie
     *
     * @param request
     * @return
     */
    public String getLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    String token = ck.getValue();
                    log.info("return cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    return token;
                }
            }
        }
        return null;
    }


    /**
     * 每次访问服务器都要重新设置session的有效期,沿用老的token
     *
     * @param user
     * @param response
     */
    private void updateToken(AuthUser user, String token, HttpServletResponse response) {
        if (user == null) {
            return;
        }
        jedisUtils.set(AuthUserKey.token, token, user);
        writeLoginToken(response, token);
    }


}
