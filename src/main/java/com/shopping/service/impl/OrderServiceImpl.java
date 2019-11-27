package com.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Address;
import com.shopping.entity.Commodity;
import com.shopping.entity.Orders;
import com.shopping.mapper.AddressMapper;
import com.shopping.mapper.OrdersMapper;
import com.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Override
    public PageInfo<Orders> findOrders(Orders orders, Integer page, Integer limit) throws SuperMarketException {
        PageHelper.startPage(page,limit);
        List<Orders> ordersList=ordersMapper.selectOrders(orders);
        if (ordersList==null){
            throw new SuperMarketException("没有符合条件的订单");
        }
        for (Orders order:ordersList){
            Integer addressid = order.getAddressid();
            String orderid = order.getOrderid();
            Address address = addressMapper.selectByPrimaryKey(addressid);
            order.setAddress(address);
            List<Map<String,Object>> orderCommodityList=ordersMapper.selectOrderCommodity(orderid);
            order.setCidAndNum(orderCommodityList);
            String cids="";
            for (Map<String,Object> map:orderCommodityList){
                Integer cid = (Integer) map.get("cid");
                cids+=cid+",";
            }
            int i = cids.lastIndexOf(",");
            String substring = cids.substring(0, i);
            List<Commodity> commodityList=ordersMapper.selectCommodityByCid(substring);
            for (Map<String,Object> map:orderCommodityList){
                Integer cid = (Integer) map.get("cid");
                for (Commodity commodity:commodityList){
                    if (commodity.getId()==cid){
                        map.put("commodity",commodity);
                    }
                }
            }
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
         orders.setSendtime(new Date());
         ordersMapper.updateOrdersStatus(orders);
    }

    @Override
    public List<Orders> findDaiSendOrder() throws SuperMarketException {
        List<Orders> ordersList=ordersMapper.selectDaiSendOrder();
        if (ordersList==null){
            throw new SuperMarketException("没有符合条件的订单");
        }
        for (Orders order:ordersList){
            Integer addressid = order.getAddressid();
            String orderid = order.getOrderid();
            Address address = addressMapper.selectByPrimaryKey(addressid);
            order.setAddress(address);
            List<Map<String,Object>> orderCommodityList=ordersMapper.selectOrderCommodity(orderid);
            order.setCidAndNum(orderCommodityList);
            String cids="";
            for (Map<String,Object> map:orderCommodityList){
                Integer cid = (Integer) map.get("cid");
                cids+=cid+",";
            }
            int i = cids.lastIndexOf(",");
            String substring = cids.substring(0, i);
            List<Commodity> commodityList=ordersMapper.selectCommodityByCid(substring);
            for (Map<String,Object> map:orderCommodityList){
                Integer cid = (Integer) map.get("cid");
                for (Commodity commodity:commodityList){
                    if (commodity.getId()==cid){
                        map.put("commodity",commodity);
                    }
                }
            }
        }
        return ordersList;
    }
}
