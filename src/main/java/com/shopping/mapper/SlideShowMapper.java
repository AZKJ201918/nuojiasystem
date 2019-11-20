package com.shopping.mapper;

import com.shopping.entity.SlideShow;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SlideShowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SlideShow record);

    int insertSelective(SlideShow record);

    SlideShow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SlideShow record);

    int updateByPrimaryKey(SlideShow record);

    List<SlideShow> selectAllSlideShow();
}