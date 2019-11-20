package com.shopping.service;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.SlideShow;

public interface SlideShowService {
    PageInfo<SlideShow> loadSlideShow(Integer page, Integer limit) throws SuperMarketException;

    void addSlideShow(SlideShow slideShow);

    void modifySlideShow(SlideShow slideShow);

    void removeSlideShow(Integer id);
}
