package com.hfut.bs.gateway.course.controller;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.model.ClassifyInfoModel;
import com.hfut.bs.course.model.CourseInfoModel;
import com.hfut.bs.course.service.IClassifyService;
import com.hfut.bs.course.service.ICourseService;
import com.hfut.bs.gateway.course.vo.ConstsClassifyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * 课程分类页
 */

@Controller
@RequestMapping("/course")
public class CourseListController {
	
	@Autowired
	private IClassifyService classifyService;
	
	@Autowired
	private ICourseService courseService;
	
	/**
	 * 课程分类页
	 * @param c 分类code
	 * @param sort 排序
	 * @param page 分页
	 */
	@RequestMapping("/list")
	public ModelAndView list(String c, String sort, TailPage page){
		ModelAndView mv = new ModelAndView("list");
		String curCode = "-1";//当前方向code
		String curSubCode = "-2";//当前分类code
		
		//加载所有课程分类
		Map<String, ConstsClassifyVO> classifyMap = queryAllClassifyMap();
		//所有一级分类
		List<ConstsClassifyVO> classifysList = new ArrayList<ConstsClassifyVO>();
		for(ConstsClassifyVO vo : classifyMap.values()){
			classifysList.add(vo);
		}
		mv.addObject("classifys", classifysList);
				
		//当前分类
		ClassifyInfoModel curClassify = classifyService.getByCode(c);
		
		if(null == curClassify){//没有此分类，加载所有二级分类
			List<ClassifyInfoModel> subClassifys = new ArrayList<ClassifyInfoModel>();
			for(ConstsClassifyVO vo : classifyMap.values()){
				subClassifys.addAll(vo.getSubClassifyList());
			}
			mv.addObject("subClassifys", subClassifys);
		}else{
			if(!"0".endsWith(curClassify.getParentCode())){//当前是二级分类
				curSubCode = curClassify.getCode();
				curCode = curClassify.getParentCode();
				mv.addObject("subClassifys", classifyMap.get(curClassify.getParentCode()).getSubClassifyList());//此分类平级的二级分类
			}else{//当前是一级分类
				curCode = curClassify.getCode();
				mv.addObject("subClassifys", classifyMap.get(curClassify.getCode()).getSubClassifyList());//此分类下的二级分类
			}
		}
		mv.addObject("curCode", curCode);
		mv.addObject("curSubCode", curSubCode);
		
		//分页排序数据
		//分页的分类参数
		CourseInfoModel queryEntity = new CourseInfoModel();
		if(!"-1".equals(curCode)){
			queryEntity.setClassify(curCode);
		}
		if(!"-2".equals(curSubCode)){
			queryEntity.setSubClassify(curSubCode);
		}
		
		//排序参数
		if("pop".equals(sort)){//最热
			page.descSortField("studyCount");
		}else{
			sort = "last";
			page.descSortField("id");
		}
		mv.addObject("sort", sort);
		
		//分页参数
		queryEntity.setOnsale(1);
		page = this.courseService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		return mv;
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
}
