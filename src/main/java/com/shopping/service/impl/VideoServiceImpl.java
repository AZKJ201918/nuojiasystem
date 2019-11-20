package com.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Video;
import com.shopping.mapper.VideoMapper;
import com.shopping.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{
    @Autowired
    private VideoMapper videoMapper;
    @Override
    public PageInfo<Video> findAllVideo(Integer page, Integer limit) throws SuperMarketException {
        PageHelper.startPage(page,limit);
        List<Video> videoList = videoMapper.selectAllVideo();
        if (videoList==null){
            throw new SuperMarketException("没有视频");
        }
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        return pageInfo;
    }

    @Override
    public void addVideo(Video video) {
        videoMapper.insertSelective(video);
    }

    @Override
    public void removeVideo(Integer id) {
        videoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void modifyVideo(Video video) {
        videoMapper.updateByPrimaryKeySelective(video);
    }
}
