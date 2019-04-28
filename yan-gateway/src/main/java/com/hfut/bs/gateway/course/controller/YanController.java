package com.hfut.bs.gateway.course.controller;

import com.hfut.bs.course.model.ClassifyInfoModel;
import com.hfut.bs.course.model.CourseInfoModel;
import com.hfut.bs.course.model.CourseQueryParam;
import com.hfut.bs.course.model.SiteCarouseInfoModel;
import com.hfut.bs.course.service.IClassifyService;
import com.hfut.bs.course.service.ICourseService;
import com.hfut.bs.course.service.ISiteCarouseService;
import com.hfut.bs.gateway.course.vo.ConstsClassifyVO;
import com.hfut.bs.user.model.UserInfoModel;
import com.hfut.bs.user.model.UserModel;
import com.hfut.bs.user.service.IAuthUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class YanController {

    @Autowired
    private ISiteCarouseService siteCarouselService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IAuthUserService authUserService;

    @Autowired
    private IClassifyService classifyService;


    /**
     * 首页
     */
    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");

        //加载轮播
        List<SiteCarouseInfoModel> carouselList = siteCarouselService.queryCarousels(4);
        mv.addObject("carouselList", carouselList);

        //课程分类(一级分类）
        List<ConstsClassifyVO> classifys = queryAllClassify();

        //课程推荐
        prepareRecomdCourses(classifys);
        mv.addObject("classifys", classifys);


        //获取5门实战课推荐，根据权重（weight）进行排序
        CourseQueryParam queryEntity = new CourseQueryParam();
        queryEntity.setCount(5);//5门
        queryEntity.setFree(0);//非免费的：实战课
        queryEntity.descSortField("weight");//按照weight降序排列
        List<CourseInfoModel> actionCourseList = courseService.queryList(queryEntity);
        mv.addObject("actionCourseList", actionCourseList);

        //获取5门免费课推荐，根据权重（weight）进行排序
        queryEntity.setFree(0);//非免费的：实战课
        List<CourseInfoModel> freeCourseList = this.courseService.queryList(queryEntity);
        mv.addObject("freeCourseList", freeCourseList);

        //获取7门java课程，根据权重（学习数量studyCount）进行排序
        queryEntity.setCount(7);
        queryEntity.setFree(null);//不分实战和免费类别
        queryEntity.setSubClassify("java");//java分类
        queryEntity.descSortField("studyCount");//按照studyCount降序排列
        List<CourseInfoModel> javaCourseList = courseService.queryList(queryEntity);
        mv.addObject("javaCourseList", javaCourseList);

        //加载讲师
        List<UserModel> recomdTeacherList = authUserService.getHomePageTeachers();
        mv.addObject("recomdTeacherList", recomdTeacherList);

        return mv;
    }

    /**
     * 获取所有，包括一级分类&二级分类
     */
    public List<ConstsClassifyVO> queryAllClassify(){
        List<ConstsClassifyVO> resultList = new ArrayList<ConstsClassifyVO>();
        for(ConstsClassifyVO vo : this.queryAllClassifyMap().values()){
            resultList.add(vo);
        }
        return resultList;
    }

    /**
     * 获取所有分类
     */
    public Map<String,ConstsClassifyVO> queryAllClassifyMap(){
        Map<String,ConstsClassifyVO> resultMap = new LinkedHashMap<String,ConstsClassifyVO>();
        Iterator<ClassifyInfoModel> it = classifyService.queryAll().iterator();
        while(it.hasNext()){
            ClassifyInfoModel c = it.next();
            if("0".equals(c.getParentCode())){//一级分类
                ConstsClassifyVO vo = new ConstsClassifyVO();
                BeanUtils.copyProperties(c, vo);
                resultMap.put(vo.getCode(), vo);
            }else{//二级分类
                if(null != resultMap.get(c.getParentCode())){
                    resultMap.get(c.getParentCode()).getSubClassifyList().add(c);//添加到子分类中
                }
            }
        }
        return resultMap;
    }


    /**
     * 为分类设置课程推荐
     */
    public void prepareRecomdCourses(List<ConstsClassifyVO> classifyVoList){
        if(CollectionUtils.isNotEmpty(classifyVoList)){
            for(ConstsClassifyVO item : classifyVoList){
                CourseQueryParam queryEntity = new CourseQueryParam();
                queryEntity.setCount(5);
                queryEntity.descSortField("weight");
                queryEntity.setClassify(item.getCode());//分类code

                List<CourseInfoModel> tmpList = this.courseService.queryList(queryEntity);
                if(CollectionUtils.isNotEmpty(tmpList)){
                    item.setRecomdCourseList(tmpList);
                }
            }
        }
    }
}
