package com.shopping.mapper;

import com.shopping.entity.Commodity;
import com.shopping.entity.Orders;
import com.shopping.entity.PrintOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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
    //@Update("update orders set status=3 where orderid=#{orderid}")
    int updateOrdersStatus(Orders orders);
    @Select("select cid,num from ordercommodity where orderid=#{orderid}")
    List<Map<String,Object>> selectOrderCommodity(String orderid);
    @Select("select id,name,subname,url from commodity where id in (${substring})")
    List<Commodity> selectCommodityByCid(@Param("substring") String substring);

    List<PrintOrder> selectDaiSendOrder();
}