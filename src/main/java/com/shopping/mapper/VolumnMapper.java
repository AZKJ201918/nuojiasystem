package com.shopping.mapper;

import com.shopping.entity.Volumn;
import org.apache.ibatis.annotations.Select;

public interface VolumnMapper {
    @Select("select volumn,totalvolumn from volumn where cid=#{id}")
    Volumn selectVolumnByCid(Integer id);
}
