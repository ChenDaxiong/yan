package com.hfut.bs.gateway.access;

import com.hfut.bs.common.redis.keys.AuthUserKey;
import com.hfut.bs.user.model.UserInfoModel;
import com.hfut.bs.gateway.common.Const;
import com.hfut.bs.gateway.redis.JedisUtils;
import com.hfut.bs.gateway.result.CodeMsg;
import com.hfut.bs.gateway.utils.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Autowired
    private JedisUtils jedisUtils;

    /**
     * 访问controller前调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandler");
        // 被@RequestMapping注解修饰的controller方法就是HanglerMethod
        // A.isAssignableFrom(B),判断A与B是否是同一个class，A是否是B的超类或者超接口，是返回true，否返回false
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            System.out.println("cat cast handler to HandlerMethod.class");
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        // 获取controller方法上的注解
        NeedLogin needLogin = method.getMethodAnnotation(NeedLogin.class);
        if (needLogin == null) {
            return true;
        }

        String paramToken = request.getParameter(Const.COOKIE_NAME);
        String cookieToken = getLoginToken(request);
        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)) {
            return false;
        }
        String token = paramToken == null ? cookieToken : paramToken;
        UserInfoModel user = getUserByToken(token, response);
        if (user == null) {
            WebUtil.render(response, CodeMsg.SERVER_ERROR);
            return false;
        }
        // 将User对象存放到本地线程变量中，方便解析器直接获取
        UserContext.setUserId(user.getId());
        return true;
    }

    /**
     * 从客户端的HttpServletRequest请求中获取到用户登陆的cookie
     *
     * @param request
     * @return
     */
    private String getLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                logger.info("read cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), Const.COOKIE_NAME)) {
                    String token = ck.getValue();
                    logger.info("return cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    return token;
                }
            }
        }
        return null;
    }

    /**
     * 根据token从缓存中取出user对象并更新Cookie的过期时间
     *
     * @param token
     * @return
     */
    public UserInfoModel getUserByToken(String token, HttpServletResponse response) {
        UserInfoModel user = jedisUtils.get(AuthUserKey.token, token, UserInfoModel.class);
        // 更新Cookie的过期时间
        updateToken(user, token, response);
        return user;
    }

    /**
     * 每次访问服务器都要重新设置session的有效期,沿用老的token
     *
     * @param user
     * @param response
     */
    private void updateToken(UserInfoModel user, String token, HttpServletResponse response) {
        if (user == null) {
            return;
        }
//        jedisUtils.set(AuthUserKey.token, token, user);
        writeLoginToken(response, token);
    }

    public void writeLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(Const.COOKIE_NAME, token);
//        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");//代表设置在根目录
        ck.setHttpOnly(true);
        //单位是秒。
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        ck.setMaxAge(60 * 60 * 24 * 365);//如果是-1，代表永久
        logger.info("write cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
    }
}
