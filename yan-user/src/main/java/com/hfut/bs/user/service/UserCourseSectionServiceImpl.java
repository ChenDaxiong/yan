package com.hfut.bs.user.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.model.CourseInfoModel;
import com.hfut.bs.user.dao.UserCourseSectionMapper;
import com.hfut.bs.user.domain.UserCourseSection;
import com.hfut.bs.user.model.UserCourseSectionInfoModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userCourseSectionService")
public class UserCourseSectionServiceImpl implements IUserCourseSectionService {


    @Autowired
    private UserCourseSectionMapper userCourseSectionMapper;

    public UserCourseSectionInfoModel getById(Integer id){
        UserCourseSection userCourseSection = userCourseSectionMapper.selectByPrimaryKey(id);
        if (userCourseSection == null){
            return null;
        }
        return toInfoModel(userCourseSection);
    }

    public List<UserCourseSectionInfoModel> queryAll(UserCourseSectionInfoModel userCourseSectionInfoModel){
        UserCourseSection queryEntity = toCourseSection(userCourseSectionInfoModel);
        List<UserCourseSection> userCourseSectionList = userCourseSectionMapper.selectAll(queryEntity);
        if (CollectionUtils.isEmpty(userCourseSectionList)){
            return null;
        }
        List<UserCourseSectionInfoModel> resultList = new ArrayList<UserCourseSectionInfoModel>();
        for (UserCourseSection item : userCourseSectionList){
            resultList.add(toInfoModel(item));
        }
        return resultList;
    }

    public UserCourseSectionInfoModel queryLatest(UserCourseSectionInfoModel userCourseSectionInfoModel){
        UserCourseSection queryEntity = toCourseSection(userCourseSectionInfoModel);
        queryEntity =  userCourseSectionMapper.selectLatest(queryEntity);
        if (queryEntity != null){
            return toInfoModel(queryEntity);
        }
        return null;
    }


    public TailPage<UserCourseSectionInfoModel> queryPage(UserCourseSectionInfoModel userCourseSectionInfoModel, TailPage page){
        UserCourseSection queryEntity = toCourseSection(userCourseSectionInfoModel);
        Integer itemsTotalCount = userCourseSectionMapper.selectTotalItemsCount(queryEntity);
        List<UserCourseSection> items = userCourseSectionMapper.selectPage(queryEntity,page);
        List<UserCourseSectionInfoModel> resultItemsList = new ArrayList<UserCourseSectionInfoModel>();
        if (CollectionUtils.isNotEmpty(items)) {
            for (UserCourseSection userCourseSection : items) {
                resultItemsList.add(toInfoModel(userCourseSection));
            }
        }
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(resultItemsList);
        return page;
    }


    public void createSelectivity(UserCourseSectionInfoModel userCourseSectionInfoModel){
        UserCourseSection entity = toCourseSection(userCourseSectionInfoModel);
        userCourseSectionMapper.insert(entity);
    }

    public void update(UserCourseSectionInfoModel userCourseSectionInfoModel){
        UserCourseSection entity = toCourseSection(userCourseSectionInfoModel);
        userCourseSectionMapper.updateByPrimaryKey(entity);
    }

    public void updateSelectivity(UserCourseSectionInfoModel userCourseSectionInfoModel){
        UserCourseSection entity = toCourseSection(userCourseSectionInfoModel);
        userCourseSectionMapper.updateByPrimaryKeySelective(entity);
    }

    public void delete(Integer id){
        userCourseSectionMapper.deleteByPrimaryKey(id);
    }


    private UserCourseSectionInfoModel toInfoModel(UserCourseSection courseSection){
        UserCourseSectionInfoModel infoModel = new UserCourseSectionInfoModel();
        BeanUtils.copyProperties(courseSection,infoModel);
        return infoModel;
    }

    private UserCourseSection toCourseSection(UserCourseSectionInfoModel infoModel){
        UserCourseSection courseSection = new UserCourseSection();
        BeanUtils.copyProperties(infoModel,courseSection);
        return courseSection;
    }

}
