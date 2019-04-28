package com.hfut.bs.gateway.user.controller;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.common.storage.QiniuStorage;
import com.hfut.bs.course.model.CourseInfoModel;
import com.hfut.bs.course.model.CourseSectionInfoModel;
import com.hfut.bs.course.service.ICourseSectionService;
import com.hfut.bs.course.service.ICourseService;
import com.hfut.bs.gateway.access.NeedLogin;
import com.hfut.bs.gateway.access.UserContext;
import com.hfut.bs.user.model.UserCourseSectionInfoModel;
import com.hfut.bs.user.model.UserInfoModel;
import com.hfut.bs.user.service.IAuthUserService;
import com.hfut.bs.gateway.result.Result;
import com.hfut.bs.user.service.IUserCourseSectionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

//@Controller
//@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IAuthUserService authUserService;

    @Autowired
    private IUserCourseSectionService userCourseSectionService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ICourseSectionService courseSectionService;



    /**
     * 首页
     */
    @RequestMapping("/home")
    public ModelAndView index(TailPage page){
        ModelAndView mv = new ModelAndView("user/home");
        mv.addObject("curNav","home");

        //加载关注用户的动态
//        UserFollowStudyRecord queryEntity = new UserFollowStudyRecord();
//        queryEntity.setUserId(SessionContext.getUserId());
//        page = userFollowsService.queryUserFollowStudyRecordPage(queryEntity, page);

        //处理用户头像
//        for(UserFollowStudyRecord item : page.getItems()){
//            if(StringUtils.isNotEmpty(item.getHeader())){
//                item.setHeader(QiniuStorage.getUrl(item.getHeader()));
//            }
//        }
        mv.addObject("page", page);

        return mv;
    }

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

    /**
     * 我的课程
     */
    @RequestMapping("/course")
    @NeedLogin
    public ModelAndView course(TailPage<UserCourseSectionInfoModel> page){
        ModelAndView mv = new ModelAndView("user/course");
        mv.addObject("curNav","course");

        UserCourseSectionInfoModel queryEntity = new UserCourseSectionInfoModel();
        queryEntity.setUserId(UserContext.getUserId());
        page = userCourseSectionService.queryPage(queryEntity, page);
        // 用户名
        for (UserCourseSectionInfoModel item : page.getItems()){
            // 用户名
            UserInfoModel user = authUserService.getById(item.getId());
            item.setUsername(user.getUsername());
            // 课程名
            CourseInfoModel course = courseService.getById(item.getCourseId());
            item.setCourseName(course.getName());
            // 章节名
            CourseSectionInfoModel courseSection = courseSectionService.getById(item.getSectionId());
            item.setSectionName(courseSection.getName());
        }
        mv.addObject("page", page);
        return mv;
    }

    /**
     * 信息
     */
    @RequestMapping("/info")
    @NeedLogin
    public ModelAndView info(){
        ModelAndView mv = new ModelAndView("user/info");
        mv.addObject("curNav","info");

        UserInfoModel authUser = authUserService.getById(UserContext.getUserId());
        if(null != authUser && StringUtils.isNotEmpty(authUser.getHeader())){
            authUser.setHeader(QiniuStorage.getUrl(authUser.getHeader()));
        }
        mv.addObject("authUser",authUser);
        return mv;
    }

    @RequestMapping("forTestUpload")
    public ModelAndView forTestUpload(){
        return new ModelAndView("/WEB-INF/forTestUpload.html");
    }


}
