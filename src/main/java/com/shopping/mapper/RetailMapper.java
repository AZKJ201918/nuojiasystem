package com.shopping.mapper;

import com.shopping.entity.Retail;
import org.apache.ibatis.annotations.Delete;

public interface RetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Retail record);

    int insertSelective(Retail record);

    Retail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Retail record);

    int updateByPrimaryKey(Retail record);

    Retail selectByCid(Integer id);
    @Delete("delete from retail where cid=#{id}")
    int deleteByCid(Integer id);
}