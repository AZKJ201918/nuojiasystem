package com.shopping.service;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Video;

public interface VideoService {
    PageInfo<Video> findAllVideo(Integer page, Integer limit) throws SuperMarketException;

    void addVideo(Video video);

    void removeVideo(Integer id);

    void modifyVideo(Video video);
}
