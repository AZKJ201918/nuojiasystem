package com.shopping.mapper;

import com.shopping.entity.Orders;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    List<Orders> selectOrders(Orders orders);
    @Select("select status from orders where orderid=#{orderid}")
    Integer selectOrderExsits(String orderid);
    @Update("update orders set status=3 where orderid=#{orderid}")
    int updateOrdersStatus(String orderid);
}