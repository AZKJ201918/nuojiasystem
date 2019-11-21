package com.shopping.mapper;

import com.shopping.entity.Commodity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommodityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);

    List<Commodity> selectCommodity(@Param("name") String name);
    @Select("select repertory from commodity where id=#{id}")
    Integer selectRepertory(Integer id);
    @Select("select volumn from volumn where cid=#{id}")
    Integer selectVolumn(Integer id);
}