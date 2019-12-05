package com.shopping.service;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Orders;
import com.shopping.entity.PrintOrder;

import java.text.ParseException;
import java.util.List;

public interface OrderService {
    PageInfo<Orders> findOrders(Orders orders, Integer page, Integer limit) throws SuperMarketException;

    void modifyOrders(Orders orders) throws SuperMarketException, ParseException;

    List<PrintOrder> findDaiSendOrder() throws SuperMarketException;
}
