package com.hfut.bs.gateway.course.vo;


import com.hfut.bs.course.model.CourseSectionInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程章节
 */
public class CourseSectionVO extends CourseSectionInfoModel {
	private static final long serialVersionUID = 180753077428934254L;

	//小节
	private List<CourseSectionInfoModel> sections = new ArrayList<CourseSectionInfoModel>();

	
	public List<CourseSectionInfoModel> getSections() {
		return sections;
	}

	public void setSections(List<CourseSectionInfoModel> sections) {
		this.sections = sections;
	}
	
}
