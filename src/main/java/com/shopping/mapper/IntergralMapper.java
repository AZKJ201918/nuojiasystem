package com.shopping.mapper;

import com.shopping.entity.Intergral;

public interface IntergralMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Intergral record);

    int insertSelective(Intergral record);

    Intergral selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Intergral record);

    int updateByPrimaryKey(Intergral record);
}