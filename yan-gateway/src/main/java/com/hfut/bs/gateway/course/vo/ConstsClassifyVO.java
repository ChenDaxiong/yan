package com.hfut.bs.gateway.course.vo;

import com.hfut.bs.course.model.ClassifyInfoModel;
import com.hfut.bs.course.model.CourseInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面展示 value object
 */
public class ConstsClassifyVO extends ClassifyInfoModel {
	
	private static final long serialVersionUID = -6898939223836635781L;

	//子分类列表
	private List<ClassifyInfoModel> subClassifyList = new ArrayList<ClassifyInfoModel>();

	//课程推荐列表
	private List<CourseInfoModel> recomdCourseList ;
	
	public List<ClassifyInfoModel> getSubClassifyList() {
		return subClassifyList;
	}
	
	public void setSubClassifyList(List<ClassifyInfoModel> subClassifyList) {
		this.subClassifyList = subClassifyList;
	}

	public List<CourseInfoModel> getRecomdCourseList() {
		return recomdCourseList;
	}

	public void setRecomdCourseList(List<CourseInfoModel> recomdCourseList) {
		this.recomdCourseList = recomdCourseList;
	}
	
}
