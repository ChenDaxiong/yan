package com.hfut.bs.course.service;

import com.hfut.bs.common.page.TailPage;
import com.hfut.bs.common.storage.QiniuStorage;
import com.hfut.bs.course.dao.SiteCarouselMapper;
import com.hfut.bs.course.domain.SiteCarousel;
import com.hfut.bs.course.model.SiteCarouseInfoModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("siteCarouseService")
public class SiteCarouseServiceImpl implements ISiteCarouseService {

    @Autowired
    private SiteCarouselMapper siteCarouselMapper;


    @Override
    public SiteCarouseInfoModel getById(Integer id) {

        SiteCarousel siteCarousel = siteCarouselMapper.selectByPrimaryKey(id);
        if (siteCarousel == null) {
            return null;
        }
        return toCarouseInfoModel(siteCarousel);
    }

    @Override
    public List<SiteCarouseInfoModel> queryCarousels(Integer count) {
        List<SiteCarousel> siteCarouselList = siteCarouselMapper.selectCarousels(count);
        if (CollectionUtils.isEmpty(siteCarouselList)) {
            return null;
        }
        List<SiteCarouseInfoModel> resultList = new ArrayList<SiteCarouseInfoModel>();
        //处理为七牛图片链接
        for (SiteCarousel item : siteCarouselList) {
            item.setPicture(QiniuStorage.getUrl(item.getPicture()));
            resultList.add(toCarouseInfoModel(item));
        }
        return resultList;
    }

    @Override
    public TailPage<SiteCarouseInfoModel> queryPage(SiteCarouseInfoModel queryEntity, TailPage page) {
        Integer itemsTotalCount = siteCarouselMapper.selectTotalItemsCount();
        List<SiteCarousel> carouselList = siteCarouselMapper.selectPage(page);
        List<SiteCarouseInfoModel> resultList = new ArrayList<SiteCarouseInfoModel>();
        if (CollectionUtils.isNotEmpty(carouselList)) {
            for (SiteCarousel item : carouselList) {
                String pictureUrl = QiniuStorage.getUrl(item.getPicture());//处理图片
                item.setPicture(pictureUrl);
                resultList.add(toCarouseInfoModel(item));
            }
        }
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(resultList);
        return page;
    }

    @Override
    public void create(SiteCarouseInfoModel entity) {
        SiteCarousel siteCarousel = toSiteCarousel(entity);
        siteCarouselMapper.insert(siteCarousel);
    }

    @Override
    public void createSelectivity(SiteCarouseInfoModel entity) {
        SiteCarousel siteCarousel = toSiteCarousel(entity);
        siteCarouselMapper.insertSelective(siteCarousel);
    }

    @Override
    public void update(SiteCarouseInfoModel entity) {
        SiteCarousel siteCarousel = toSiteCarousel(entity);
        siteCarouselMapper.updateByPrimaryKey(siteCarousel);
    }

    @Override
    public void updateSelectivity(SiteCarouseInfoModel entity) {
        SiteCarousel siteCarousel = toSiteCarousel(entity);
        siteCarouselMapper.updateByPrimaryKeySelective(siteCarousel);
    }

    @Override
    public void delete(Integer id) {
        siteCarouselMapper.deleteByPrimaryKey(id);
    }


    private SiteCarouseInfoModel toCarouseInfoModel(SiteCarousel siteCarousel) {
        SiteCarouseInfoModel result = new SiteCarouseInfoModel();
        BeanUtils.copyProperties(siteCarousel, result);
        return result;
    }

    private SiteCarousel toSiteCarousel(SiteCarouseInfoModel carouseInfoModel) {
        SiteCarousel result = new SiteCarousel();
        BeanUtils.copyProperties(carouseInfoModel, result);
        return result;
    }

}
