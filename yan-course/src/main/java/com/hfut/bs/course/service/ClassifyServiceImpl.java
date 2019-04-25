package com.hfut.bs.course.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.course.model.ClassifyInfoModel;
import com.hfut.bs.course.dao.ClassifyMapper;
import com.hfut.bs.course.domain.Classify;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("classifyService")
public class ClassifyServiceImpl implements IClassifyService {


    @Autowired
    private ClassifyMapper classifyMapper;

    public ClassifyInfoModel getById(Integer id){
        Classify classify = classifyMapper.selectByPrimaryKey(id);
        if (classify == null){
            return null;
        }
        return toClassifyInfoModel(classify);
    }

    public List<ClassifyInfoModel> queryAll(){
        List<Classify> classifyList = classifyMapper.selectAll();
        if (CollectionUtils.isEmpty(classifyList)){
            return null;
        }
        List<ClassifyInfoModel> resultList = new ArrayList<ClassifyInfoModel>();
        for (Classify classify : classifyList){
            resultList.add(toClassifyInfoModel(classify));
        }
        return resultList;
    }

    @Override
    public List<ClassifyInfoModel> queryByCondition(ClassifyInfoModel classifyInfoModel){
        Classify queryEntity = toClassify(classifyInfoModel);
        List<Classify>  classifyList = classifyMapper.selectByCondition(queryEntity);
        if (CollectionUtils.isEmpty(classifyList)){
            return null;
        }
        List<ClassifyInfoModel> resultList = new ArrayList<ClassifyInfoModel>();
        for (Classify classify : classifyList){
            resultList.add(toClassifyInfoModel(classify));
        }
        return resultList;
    }

    @Override
    public ClassifyInfoModel getByCode(String code){
        if(StringUtils.isEmpty(code))
            return null;
        Classify queryEntity = new Classify();
        queryEntity.setCode(code);
        List<Classify> list = classifyMapper.selectByCondition(queryEntity);
        if(CollectionUtils.isNotEmpty(list)){
            return toClassifyInfoModel(list.get(0));
        }
        return null;
    }

    public TailPage<ClassifyInfoModel> queryPage(ClassifyInfoModel classifyInfoModel, TailPage page){
        Classify queryEntity = toClassify(classifyInfoModel);
        Integer itemsTotalCount = classifyMapper.selectTotalItemsCount(queryEntity);
        List<Classify> classifyList = classifyMapper.selectPage(page);
        page.setItemsTotalCount(itemsTotalCount);
        if (classifyList == null){
            page.setItems(null);
        }
        List<ClassifyInfoModel> resultList = new ArrayList<ClassifyInfoModel>();
        for (Classify classify : classifyList){
            resultList.add(toClassifyInfoModel(classify));
        }
        page.setItems(resultList);
        return page;
    }

    @Override
    public void create(ClassifyInfoModel entity){
        Classify classify = toClassify(entity);
        classifyMapper.insert(classify);
    }

    @Override
    public void createSelectivity(ClassifyInfoModel entity){
        Classify classify = toClassify(entity);
        classifyMapper.insertSelective(classify);
    }

    @Override
    public void updateSelectivity(ClassifyInfoModel entity){
        Classify classify = toClassify(entity);
        classifyMapper.updateByPrimaryKeySelective(classify);
    }

    @Override
    public void delete(Integer id){
        classifyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteLogic(Integer id){
        classifyMapper.deleteLogic(id);
    }

    private Classify toClassify(ClassifyInfoModel classifyInfoModel){
        Classify classify = new Classify();
        BeanUtils.copyProperties(classifyInfoModel,classify);
        return classify;
    }

    private ClassifyInfoModel toClassifyInfoModel(Classify classify){
        ClassifyInfoModel classifyInfoModel = new ClassifyInfoModel();
        BeanUtils.copyProperties(classify,classifyInfoModel);
        return classifyInfoModel;
    }
}
