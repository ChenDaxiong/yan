package com.hfut.bs.course.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.dao.CollegeMapper;
import com.hfut.bs.course.domain.College;
import com.hfut.bs.course.model.CollegeInfoModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("collegeService")
public class CollegeServiceImpl implements ICollegeService {

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public CollegeInfoModel getById(Integer id) {
        College college = collegeMapper.selectByPrimaryKey(id);
        if (college == null) {
            return null;
        }
        return toCollegeInfoModel(college);
    }

    @Override
    public CollegeInfoModel getByCode(String code) {
        College college = collegeMapper.selectByCode(code);
        if (college == null) {
            return null;
        }
        return toCollegeInfoModel(college);
    }

    @Override
    public List<CollegeInfoModel> queryAll(CollegeInfoModel collegeInfoModel) {
        College queryEntity = toCollege(collegeInfoModel);
        List<College> collegeList = collegeMapper.selectAll(queryEntity);
        if (CollectionUtils.isEmpty(collegeList)) {
            return null;
        }
        List<CollegeInfoModel> resultList = new ArrayList<CollegeInfoModel>();
        for (College college : collegeList) {
            resultList.add(toCollegeInfoModel(college));
        }
        return resultList;
    }

    @Override
    public TailPage<CollegeInfoModel> queryPage(CollegeInfoModel collegeInfoModel, TailPage page) {
        College queryEntity = toCollege(collegeInfoModel);
        Integer itemsTotalCount = collegeMapper.selectTotalItemsCount(queryEntity);
        List<College> collegeList = collegeMapper.selectPage(queryEntity, page);
        List<CollegeInfoModel> resultList = new ArrayList<CollegeInfoModel>();
        if (CollectionUtils.isNotEmpty(collegeList)) {
            for (College college : collegeList) {
                resultList.add(toCollegeInfoModel(college));
            }
        }
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(resultList);
        return page;
    }

    @Override
    public void create(CollegeInfoModel entity) {
        College college = toCollege(entity);
        collegeMapper.insert(college);
    }

    @Override
    public void createSelectivity(CollegeInfoModel entity) {
        College college = toCollege(entity);
        collegeMapper.insertSelective(college);
    }

    @Override
    public void update(CollegeInfoModel entity) {
        College college = toCollege(entity);
        collegeMapper.updateByPrimaryKey(college);
    }

    @Override
    public void updateSelectivity(CollegeInfoModel entity) {
        College college = toCollege(entity);
        collegeMapper.updateByPrimaryKeySelective(college);
    }

    @Override
    public void delete(Integer id) {
        collegeMapper.deleteByPrimaryKey(id);
    }

    private College toCollege(CollegeInfoModel collegeInfoModel) {
        College college = new College();
        BeanUtils.copyProperties(collegeInfoModel, college);
        return college;
    }

    private CollegeInfoModel toCollegeInfoModel(College college) {
        CollegeInfoModel collegeInfoModel = new CollegeInfoModel();
        BeanUtils.copyProperties(college, collegeInfoModel);
        return collegeInfoModel;
    }

}
