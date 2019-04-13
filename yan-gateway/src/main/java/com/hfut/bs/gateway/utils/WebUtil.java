package com.hfut.bs.gateway.utils;

import com.hfut.bs.common.utils.JsonUtil;
import com.hfut.bs.gateway.result.CodeMsg;
import com.hfut.bs.gateway.result.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class WebUtil {

    /**
     * 拦截器拦截请求以后返回出错提示给客户端
     */
    public static void render(HttpServletResponse response, CodeMsg codeMsg) throws Exception {
        // 第一步先拿到输出流
        OutputStream out = response.getOutputStream();
        String str = JsonUtil.beanToString(Result.error(codeMsg));
        // 如果不指定输出的编码方式，客户端将会乱码
        response.setContentType("application/json;charset = UTF-8");
        // 默认是UTF-8的编码
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
