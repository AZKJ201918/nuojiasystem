package com.shopping.mapper;

import com.shopping.entity.Commodity;
import com.shopping.entity.Link;
import com.shopping.entity.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OptionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Options record);

    int insertSelective(Options record);

    Options selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Options record);

    int updateByPrimaryKey(Options record);

    List<Options> selectAllOption();
    @Select("select id,name,url from link")
    List<Link> selectAllLink();
    @Select("<script>select id,name,url from commodity <if test='name!=null'> where name like '%' #{name} '%'</if></script>")
    List<Commodity> selectLikeCommodity(@Param("name") String name);
}