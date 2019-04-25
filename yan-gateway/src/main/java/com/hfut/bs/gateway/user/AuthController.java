package com.hfut.bs.gateway.user;

import com.alibaba.dubbo.rpc.RpcContext;
import com.hfut.bs.common.redis.keys.AuthUserKey;
import com.hfut.bs.common.utils.JsonView;
import com.hfut.bs.common.utils.UUIDUtil;
import com.hfut.bs.gateway.access.UserContext;
import com.hfut.bs.gateway.redis.JedisUtils;
import com.hfut.bs.gateway.utils.CookieUtil;
import com.hfut.bs.user.model.UserInfoModel;
import com.hfut.bs.user.model.UserModel;
import com.hfut.bs.user.service.IAuthUserService;
import com.hfut.bs.common.utils.CommonUtil;
import com.hfut.bs.common.utils.MD5Utils;
import com.hfut.bs.gateway.result.CodeMsg;
import com.hfut.bs.gateway.result.ResponseVo;
import com.hfut.bs.gateway.result.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/auth/")
@Controller
public class AuthController {

     @Autowired
     private IAuthUserService authUserService;

     @Autowired
     JedisUtils jedisUtils;

     @Autowired
    CookieUtil cookieUtil;

    @RequestMapping(value="index")
    public String index(HttpServletRequest request, HttpServletResponse response){
        return "index";
    }


    @RequestMapping("to_login")
    public ModelAndView to_login(){
        return new ModelAndView("auth/login");
    }


//    @RequestMapping("register")
//    @ResponseBody
    public Result register(UserInfoModel userInfoModel) {
        UserModel user = authUserService.getByUsername(userInfoModel.getUsername());
        if (user != null) {
            return Result.error(CodeMsg.USERNAME_IS_EXISTED);
        }
        String dbSalt = CommonUtil.getUID();
        String dbPassword = MD5Utils.formPassToDbPass(userInfoModel.getPassword(), dbSalt);
        userInfoModel.setSalt(dbSalt);
        userInfoModel.setPassword(dbPassword);
        authUserService.createSelectivity(userInfoModel);
        return Result.success("注册成功");
    }

//
//    @RequestMapping("login")
//    @ResponseBody
    public Result do_login(UserInfoModel userInfoModel,HttpServletResponse response) {
        if (StringUtils.isEmpty(userInfoModel.getPassword()) || StringUtils.isEmpty(userInfoModel.getUsername())) {
            //  用户名和密码不能为空
            return Result.error(CodeMsg.LOGIN_PARAMETER_EMPTY);
        }
        RpcContext.getContext().setResponse(response);
        UserInfoModel user = authUserService.getByUsernameAndPassword(userInfoModel);
        // 将登陆的用户cookie种到客户端浏览器
        addCookie(response, user);
        return Result.success(user);
    }


    @RequestMapping(value = "updateUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserInfo(UserInfoModel userInfoModel){
        boolean isSuccess = authUserService.updateSelectivity(userInfoModel);
        if (isSuccess){
            return Result.success(authUserService.getById(userInfoModel.getId()));
        }
        return Result.error(CodeMsg.USERINFO_UPDATE_FAILED);
    }




    @RequestMapping("checkUsername")
    @ResponseBody
    public ResponseVo checkUsername(String username){
        UserModel user = authUserService.getByUsername(username);
        if (user == null){
            return ResponseVo.success(true);
        }
        return ResponseVo.success(false);
    }

    @RequestMapping("hello")
    public String testFreemarker(){
        return "hello";
    }

    /**
     * 注册页面
     */
    @RequestMapping(value = "/register")
    public  ModelAndView register(){
        if(UserContext.getUserId() != null){
            return new ModelAndView("redirect:/index.html");
        }
        return new ModelAndView("auth/register");
    }

    /**
     * 实现注册
     */
    @RequestMapping(value = "/doRegister")
    @ResponseBody
    public String doRegister(UserInfoModel authUser, HttpServletRequest request) {
        //验证码判断
//        if(identiryCode!=null && !identiryCode.equalsIgnoreCase(SessionContext.getIdentifyCode(request))){
//            return JsonView.render(2);
//        }

        UserModel tmpUser = authUserService.getByUsername(authUser.getUsername());
        if(tmpUser != null){
            return JsonView.render(1);
        }else{
            String dbSalt = CommonUtil.getUID();
            String formPassword = MD5Utils.inputPassToFormPass(authUser.getPassword());
            authUser.setPassword(MD5Utils.formPassToDbPass(formPassword,dbSalt));
            authUser.setSalt(dbSalt);
            authUserService.createSelectivity(authUser);
            return JsonView.render(0);
        }
    }

    /**
     * 登录页面
     */
    @RequestMapping(value = "/login")
    public  ModelAndView login(){
        if(UserContext.getUserId() != null){
            return new ModelAndView("redirect:/index.html");
        }
        return new ModelAndView("auth/login");
    }

    /**
     * ajax登录
     */
    @RequestMapping(value = "/ajaxlogin")
    @ResponseBody
    public String ajaxLogin(UserInfoModel user, String identiryCode, Integer rememberMe, HttpServletResponse response){
//        //验证码判断
//        if(identiryCode!=null && !identiryCode.equalsIgnoreCase(SessionContext.getIdentifyCode(request))){
//            return JsonView.render(2, "验证码不正确！");
//        }
        if (StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getUsername())) {
            //  用户名和密码不能为空
            return JsonView.render(1,"用户名和密码不能为空");
        }

        try {
            // 转化成表单密码
            user.setPassword(MD5Utils.inputPassToFormPass(user.getPassword()));
            UserInfoModel userDetail = authUserService.getByUsernameAndPassword(user);
            // 将登陆的用户cookie种到客户端浏览器
            addCookie(response, userDetail);
            return new JsonView().toString();
        }catch(AuthenticationException e){ //登录失败
            return JsonView.render(1, "用户名或密码不正确");
        }
    }

    @RequestMapping(value = "/doLogin")
    public ModelAndView doLogin(UserInfoModel user, String identiryCode, HttpServletResponse response){

        //如果已经登录过
        if(UserContext.getUserId() != null){
            return new ModelAndView("redirect:/user/home.html");
        }

        //验证码判断
//        if(identiryCode!=null && !identiryCode.equalsIgnoreCase(SessionContext.getIdentifyCode(request))){
//            ModelAndView mv = new ModelAndView("auth/login");
//            mv.addObject("errcode", 1);
//            return mv;
//        }
//        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),EncryptUtil.encodedByMD5(user.getPassword()));
        try {
            user.setPassword(MD5Utils.inputPassToFormPass(user.getPassword()));
            UserInfoModel userDetail = authUserService.getByUsernameAndPassword(user);
            // 将登陆的用户cookie种到客户端浏览器
            addCookie(response, userDetail);
            return new ModelAndView("redirect:/user/home.html");
        }catch(AuthenticationException e){ //登录失败
            ModelAndView mv = new ModelAndView("auth/login");
            mv.addObject("errcode", 2);
            return mv;
        }
    }

    /**
     * 在redis中缓存cookie和往客户端浏览器种cookie
     *
     * @param response
     * @param user
     */
    private void addCookie(HttpServletResponse response, UserInfoModel user) {
        String token = UUIDUtil.uuid();
        cookieUtil.writeLoginToken(response, token);
        jedisUtils.set(AuthUserKey.token, token, user);
    }




}
