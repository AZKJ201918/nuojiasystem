package com.shopping.mapper;

import com.shopping.entity.DeatilBanner;

import java.util.List;

public interface DeatilBannerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeatilBanner record);

    int insertSelective(DeatilBanner record);

    DeatilBanner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeatilBanner record);

    int updateByPrimaryKey(DeatilBanner record);

    List<DeatilBanner> selectDetailBannerByCid(Integer id);
}