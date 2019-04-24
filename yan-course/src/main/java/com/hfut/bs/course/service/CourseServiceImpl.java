package com.hfut.bs.course.service;


import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.common.storage.QiniuStorage;
import com.hfut.bs.course.consts.CourseEnum;
import com.hfut.bs.course.dao.CourseMapper;
import com.hfut.bs.course.domain.Course;
import com.hfut.bs.course.model.CourseInfoModel;
import com.hfut.bs.course.model.CourseQueryParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("courseService")
public class CourseServiceImpl implements ICourseService{

    @Autowired
    private CourseMapper courseMapper;

//    @Override
    public CourseInfoModel getById(Integer id){
        Course course = courseMapper.selectByPrimaryKey(id);
        prepareCoursePicture(course);
        return toCourseInfoModel(course);
    }



    @Override
    public List<CourseInfoModel> queryList(CourseQueryParam queryEntity){
        if(null == queryEntity.getOnsale()){//是否上架
            queryEntity.setOnsale(CourseEnum.ONSALE.value());
        }
        List<Course> courseList = courseMapper.selectList(queryEntity);
        List<CourseInfoModel> resultList = new ArrayList<>();
        for (Course course : courseList){
            resultList.add(toCourseInfoModel(course));
        }
        return resultList;
    }

    @Override
    public TailPage<CourseInfoModel> queryPage(CourseInfoModel queryEntity , TailPage page){
        Course course = toCourse(queryEntity);
        Integer itemsTotalCount = courseMapper.selectTotalItemsCount(course);
        List<Course> items = courseMapper.selectPage(course,page);
        if(CollectionUtils.isNotEmpty(items)){
            for(Course item : items){
                prepareCoursePicture(item);
            }
        }
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }

    @Override
    public void createSelectivity(CourseInfoModel entity){
        Course course = toCourse(entity);
        courseMapper.insertSelective(course);
    }

    @Override
    public void updateSelectivity(CourseInfoModel entity){
        Course course = toCourse(entity);
        courseMapper.updateByPrimaryKeySelective(course);
    }

    //物理删除
    @Override
    public void delete(Integer id){
        courseMapper.deleteByPrimaryKey(id);
    }

    //逻辑删除
    @Override
    public void deleteLogic(Integer id){
        courseMapper.deleteLogic(id);
    }

    private void prepareCoursePicture(Course course){
        if(null != course && StringUtils.isNotEmpty(course.getPicture())){
            course.setPicture(QiniuStorage.getUrl(course.getPicture()));
        }
    }

    private CourseInfoModel toCourseInfoModel(Course course){
        CourseInfoModel courseInfoModel = new CourseInfoModel();
        BeanUtils.copyProperties(course,courseInfoModel);
        return courseInfoModel;
    }

    private Course toCourse(CourseInfoModel courseInfoModel){
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoModel,course);
        return course;
    }
}
