package com.shopping.mapper;

import com.shopping.entity.Commercial;
import org.apache.ibatis.annotations.Delete;

public interface CommercialMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Commercial record);

    int insertSelective(Commercial record);

    Commercial selectByCid(Integer id);

    int updateByPrimaryKeySelective(Commercial record);

    int updateByPrimaryKey(Commercial record);
    @Delete("delete from commercial where cid =#{id}")
    int deleteByCid(Integer id);
}