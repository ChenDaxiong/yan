package com.hfut.bs.gateway.course.controller;

import com.hfut.bs.common.storage.QiniuStorage;
import com.hfut.bs.common.utils.JsonView;
import com.hfut.bs.course.model.CourseInfoModel;
import com.hfut.bs.course.model.CourseQueryParam;
import com.hfut.bs.course.model.CourseSectionInfoModel;
import com.hfut.bs.course.service.ICourseSectionService;
import com.hfut.bs.course.service.ICourseService;
import com.hfut.bs.gateway.access.NeedLogin;
import com.hfut.bs.gateway.access.UserContext;
import com.hfut.bs.gateway.course.vo.CourseSectionVO;
import com.hfut.bs.gateway.utils.SessionContext;
import com.hfut.bs.user.model.UserCourseSectionInfoModel;
import com.hfut.bs.user.model.UserInfoModel;
import com.hfut.bs.user.model.UserModel;
import com.hfut.bs.user.service.IAuthUserService;
import com.hfut.bs.user.service.IUserCourseSectionService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * 课程详情信息
 */
@Controller
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private IAuthUserService authUserService;
	
	@Autowired
	private ICourseSectionService courseSectionService;
	
	@Autowired
	private IUserCourseSectionService userCourseSectionService;
	
	
	/**
	 * 课程章节页面
	 * @return
	 */
	@RequestMapping("/learn/{courseId}")
	public ModelAndView learn(@PathVariable Integer courseId){
		if(null == courseId)
			return new ModelAndView("error/404"); 
		
		//获取课程
		CourseInfoModel course = courseService.getById(courseId);
		if(null == course)
			return new ModelAndView("error/404"); 
		
		//获取课程章节
		ModelAndView mv = new ModelAndView("learn");
		List<CourseSectionVO> chaptSections = queryCourseSection(courseId);
		mv.addObject("course", course);
		mv.addObject("chaptSections", chaptSections);
		
		//获取讲师
		UserModel courseTeacher = authUserService.getByUsername(course.getUsername());
		if(null != courseTeacher && StringUtils.isNotEmpty(courseTeacher.getHeader())){
			courseTeacher.setHeader(QiniuStorage.getUrl(courseTeacher.getHeader()));
		}
		mv.addObject("courseTeacher", courseTeacher);
		
		//获取推荐课程
		CourseQueryParam queryEntity = new CourseQueryParam();
		queryEntity.descSortField("weight");
		queryEntity.setCount(5);//5门推荐课程
		queryEntity.setSubClassify(course.getSubClassify());
		List<CourseInfoModel> recomdCourseList = courseService.queryList(queryEntity);
		mv.addObject("recomdCourseList", recomdCourseList);
		
		//当前学习的章节
		UserCourseSectionInfoModel userCourseSectionInfoModel = new UserCourseSectionInfoModel();
		userCourseSectionInfoModel.setCourseId(course.getId());
		userCourseSectionInfoModel.setUserId(UserContext.getUserId());
		userCourseSectionInfoModel = userCourseSectionService.queryLatest(userCourseSectionInfoModel);
		if(null != userCourseSectionInfoModel){
			CourseSectionInfoModel curCourseSection = this.courseSectionService.
					getById(userCourseSectionInfoModel.getSectionId());
			mv.addObject("curCourseSection", curCourseSection);
		}
		
		return mv;
	}
	
	
	/**
	 * 视频学习页面
	 * @return
	 */
	@RequestMapping("/video/{sectionId}")
	public ModelAndView video(@PathVariable Integer sectionId){
		if(null == sectionId)
			return new ModelAndView("error/404"); 
		
		CourseSectionInfoModel courseSection = courseSectionService.getById(sectionId);
		if(null == courseSection)
			return new ModelAndView("error/404"); 
		
		//课程章节
		ModelAndView mv = new ModelAndView("video");
		List<CourseSectionVO> chaptSections = queryCourseSection(courseSection.getCourseId());
		mv.addObject("courseSection", courseSection);
		mv.addObject("chaptSections", chaptSections);
		
		//学习记录
		UserCourseSectionInfoModel userCourseSection = new UserCourseSectionInfoModel();
		userCourseSection.setUserId(SessionContext.getUserId());
		userCourseSection.setCourseId(courseSection.getCourseId());
		userCourseSection.setSectionId(courseSection.getId());
		UserCourseSectionInfoModel result = userCourseSectionService.queryLatest(userCourseSection);
		// 根据UserId获取User的信息
		UserInfoModel user = authUserService.getById(SessionContext.getUserId());
		if(null == result){//如果没有，插入
			userCourseSection.setCreateTime(new Date());
			userCourseSection.setCreateUser(user.getUsername());
			userCourseSection.setUpdateTime(new Date());
			userCourseSection.setUpdateUser(user.getUsername());
			if (userCourseSection.getStatus() != null && !Integer.valueOf(0).equals(userCourseSection.getStatus())){
				userCourseSection.setStatus(0);
			}else {
				userCourseSection.setStatus(1);
			}
			
			userCourseSectionService.createSelectivity(userCourseSection);
		}else{
			result.setUpdateTime(new Date());
			userCourseSectionService.update(result);
		}
		return mv;
	}
	
	@RequestMapping(value = "/getCurLeanInfo")
	@NeedLogin
	@ResponseBody
	public String getCurLeanInfo(){
		JsonView jv = new JsonView();
		//加载当前用户学习最新课程
			UserCourseSectionInfoModel userCourseSection = new UserCourseSectionInfoModel();
			userCourseSection.setUserId(UserContext.getUserId());
			userCourseSection = this.userCourseSectionService.queryLatest(userCourseSection);
			if(null != userCourseSection){
				JSONObject jsObj = new JSONObject();
				CourseSectionInfoModel curCourseSection = this.courseSectionService.getById(userCourseSection.getSectionId());
				jsObj.put("curCourseSection", curCourseSection);
				CourseInfoModel curCourse = courseService.getById(userCourseSection.getCourseId());
				jsObj.put("curCourse", curCourse);
				jv.setData(jsObj);
			}
		return jv.toString();
	}

	/**
	 * 获取课程章节
	 *
	 * @param courseId
	 * @return
	 */
	public List<CourseSectionVO> queryCourseSection(Integer courseId){
		List<CourseSectionVO> resultList = new ArrayList<CourseSectionVO>();
		CourseSectionInfoModel queryEntity = new CourseSectionInfoModel();
		queryEntity.setCourseId(courseId);
		queryEntity.setOnsale(true);//上架

		Map<Integer,CourseSectionVO> tmpMap = new LinkedHashMap<Integer, CourseSectionVO>();
		Iterator<CourseSectionInfoModel> it = courseSectionService.queryAll(queryEntity).iterator();
		while(it.hasNext()){
			CourseSectionInfoModel item = it.next();
			if(Integer.valueOf(0).equals(item.getParentId())){//章
				CourseSectionVO vo = new CourseSectionVO();
				BeanUtils.copyProperties(item, vo);
				tmpMap.put(vo.getId(), vo);
			}else{
				tmpMap.get(item.getParentId()).getSections().add(item);//小节添加到大章中
			}
		}
		for(CourseSectionVO vo : tmpMap.values()){
			resultList.add(vo);
		}
		return resultList;
	}
	
}
