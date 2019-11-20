package com.shopping.service;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Orders;

public interface OrderService {
    PageInfo<Orders> findOrders(Orders orders, Integer page, Integer limit) throws SuperMarketException;

    void modifyOrders(Orders orders) throws SuperMarketException;
}
