package com.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Orders;
import com.shopping.mapper.OrdersMapper;
import com.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Override
    public PageInfo<Orders> findOrders(Orders orders, Integer page, Integer limit) throws SuperMarketException {
        PageHelper.startPage(page,limit);
        List<Orders> ordersList=ordersMapper.selectOrders(orders);
        if (ordersList==null){
            throw new SuperMarketException("没有符合条件的订单");
        }
        PageInfo<Orders> pageInfo = new PageInfo<>(ordersList);
        return pageInfo;
    }

    @Override
    public void modifyOrders(Orders orders) throws SuperMarketException {
         Integer status=ordersMapper.selectOrderExsits(orders.getOrderid());
         if (status!=2){
             throw new SuperMarketException("不能修改未付款的订单为已发货");
         }
         ordersMapper.updateOrdersStatus(orders.getOrderid());
    }
}
