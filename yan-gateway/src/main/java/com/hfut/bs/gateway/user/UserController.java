package com.hfut.bs.gateway.user;

import com.hfut.bs.common.storage.QiniuStorage;
import com.hfut.bs.gateway.access.NeedLogin;
import com.hfut.bs.gateway.access.UserContext;
import com.hfut.bs.user.model.UserInfoModel;
import com.hfut.bs.user.service.IAuthUserService;
import com.hfut.bs.gateway.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IAuthUserService authUserService;

    @RequestMapping(value = "uploadHead",method = RequestMethod.POST)
    @NeedLogin
    @ResponseBody
    public Result uploadHead(@RequestParam MultipartFile pictureImg){
        try {
            UserInfoModel userInfoModel = authUserService.getById(UserContext.getUserId());
            userInfoModel.setId(UserContext.getUserId());
            if (null != pictureImg && pictureImg.getBytes().length > 0) {
                String key = QiniuStorage.uploadImage(pictureImg.getBytes());
                userInfoModel.setHeader(key);
            }
            authUserService.updateSelectivity(userInfoModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success("上传成功");
    }

    @RequestMapping("forTestUpload")
    public ModelAndView forTestUpload(){
        return new ModelAndView("forTestUpload");
    }


}
