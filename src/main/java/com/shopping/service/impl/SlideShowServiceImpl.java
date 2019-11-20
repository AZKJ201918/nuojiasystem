package com.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.SlideShow;
import com.shopping.mapper.SlideShowMapper;
import com.shopping.service.SlideShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SlideShowServiceImpl implements SlideShowService {
    @Autowired
    private SlideShowMapper slideShowMapper;
    @Override
    public PageInfo<SlideShow> loadSlideShow(Integer page, Integer limit) throws SuperMarketException {
        PageHelper.startPage(page,limit);
        List<SlideShow> slideShowList = slideShowMapper.selectAllSlideShow();
        if (slideShowList==null){
            throw new SuperMarketException("没有轮播图");
        }
        PageInfo<SlideShow> pageInfo = new PageInfo<>(slideShowList);
        return pageInfo;
    }

    @Override
    public void addSlideShow(SlideShow slideShow) {
        slideShowMapper.insertSelective(slideShow);
    }

    @Override
    public void modifySlideShow(SlideShow slideShow) {
        slideShowMapper.updateByPrimaryKeySelective(slideShow);
    }

    @Override
    public void removeSlideShow(Integer id) {
        slideShowMapper.deleteByPrimaryKey(id);
    }
}
