package com.hfut.bs.course.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.dao.CourseSectionMapper;
import com.hfut.bs.course.domain.CourseSection;
import com.hfut.bs.course.model.CourseSectionInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("courseSectionService")
public class CourseSectionServiceImpl implements ICourseSectionService {

    @Autowired
    private CourseSectionMapper sectionMapper;

    public CourseSectionInfoModel getById(Integer id){
        CourseSection section = sectionMapper.selectByPrimaryKey(id);
        if (section == null)
            return null;
        return toSectionInfoModel(section);
    }

    @Override
    public List<CourseSectionInfoModel> queryAll(CourseSectionInfoModel sectionInfoModel){
        CourseSection queryEntity = toCourseSection(sectionInfoModel);
        List<CourseSection> courseSections = sectionMapper.selectAll(queryEntity);
        if (courseSections == null){
            return null;
        }
        List<CourseSectionInfoModel>  resultList = new ArrayList<>();
        for (CourseSection courseSection : courseSections){
            resultList.add(toSectionInfoModel(courseSection));
        }
        return resultList;
    }

    /**
     * 获取课程章最大的sort
     */
    public Integer getMaxSort(Integer courseId){
        return sectionMapper.selectMaxSort(courseId);
    }

    public TailPage<CourseSectionInfoModel> queryPage(CourseSectionInfoModel sectionInfoModel , TailPage page){
        CourseSection queryEntity = toCourseSection(sectionInfoModel);
        Integer itemsTotalCount = sectionMapper.selectTotalItemsCount();
        List<CourseSection> items = sectionMapper.selectPage(queryEntity,page);
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }

    public void createSelectivity(CourseSectionInfoModel sectionInfoModel){
        CourseSection entity = toCourseSection(sectionInfoModel);
        sectionMapper.insertSelective(entity);
    }

    /**
     *批量创建
     **/
    public void createList(List<CourseSectionInfoModel> sectionInfoModelList){
        List<CourseSection> entityList = sectionInfoModelList.stream().map(sectionInfoModel -> toCourseSection(sectionInfoModel)).collect(Collectors.toList());
        sectionMapper.insertList(entityList);
    }

    public void update(CourseSectionInfoModel sectionInfoModel){
        sectionMapper.updateByPrimaryKey(toCourseSection(sectionInfoModel));
    }

    public void updateSelectivity(CourseSectionInfoModel sectionInfoModel){
        sectionMapper.updateByPrimaryKeySelective(toCourseSection(sectionInfoModel));
    }

    public void delete(Integer id){
        sectionMapper.deleteByPrimaryKey(id);
    }

    public void deleteLogic(Integer id){
        sectionMapper.deleteLogic(id);
    }

    /**
     * 比当前sort大的，正序排序的第一个
     * @param curCourseSectionInfoModel
     * @return
     */
    public CourseSectionInfoModel getSortSectionMax(CourseSectionInfoModel curCourseSectionInfoModel){
        CourseSection curCourseSection = toCourseSection(curCourseSectionInfoModel);
        curCourseSection = sectionMapper.selectSortSectionMax(curCourseSection);
        if (curCourseSection !=   null){
            return toSectionInfoModel(curCourseSection);
        }
        return null;
    }

    /**
     * 比当前sort小的，倒序排序的第一个
     * @param curCourseSectionInfoModel
     * @return
     */
    public CourseSectionInfoModel getSortSectionMin(CourseSectionInfoModel curCourseSectionInfoModel){
        CourseSection curCourseSection = toCourseSection(curCourseSectionInfoModel);
        curCourseSection = sectionMapper.selectSortSectionMin(curCourseSection);
        if (curCourseSection !=   null){
            return toSectionInfoModel(curCourseSection);
        }
        return null;
    }

    private CourseSection toCourseSection(CourseSectionInfoModel courseSectionInfoModel){
        CourseSection section = new CourseSection();
        BeanUtils.copyProperties(courseSectionInfoModel,section);
        return section;
    }

    private CourseSectionInfoModel toSectionInfoModel(CourseSection section){
        CourseSectionInfoModel sectionInfoModel = new CourseSectionInfoModel();
        BeanUtils.copyProperties(section,sectionInfoModel);
        return sectionInfoModel;
    }


}
