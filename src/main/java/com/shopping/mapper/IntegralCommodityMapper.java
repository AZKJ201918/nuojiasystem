package com.shopping.mapper;

import com.shopping.entity.Commercial;
import com.shopping.entity.IntegralCommodity;
import org.apache.ibatis.annotations.Delete;

public interface IntegralCommodityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IntegralCommodity record);

    int insertSelective(IntegralCommodity record);

    IntegralCommodity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IntegralCommodity record);

    int updateByPrimaryKey(IntegralCommodity record);

    IntegralCommodity selectByCid(Integer id);
    @Delete("delete from integralcommodity where cid =#{id}")
    int deleteByCid(Integer id);
}