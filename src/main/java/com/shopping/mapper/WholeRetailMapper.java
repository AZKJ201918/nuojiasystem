package com.shopping.mapper;

import com.shopping.entity.WholeRetail;
import org.apache.ibatis.annotations.Delete;

public interface WholeRetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WholeRetail record);

    int insertSelective(WholeRetail record);

    WholeRetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WholeRetail record);

    int updateByPrimaryKey(WholeRetail record);

    WholeRetail selectByCid(Integer id);
    @Delete("delete from wholeretail where cid=#{id}")
    int deleteByCid(Integer id);
}