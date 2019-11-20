package com.shopping.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.Orders;
import com.shopping.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "订单管理模块")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @ApiOperation(value = "查看全部订单",notes = "支持模糊查询",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/allOrder")
    public ApiResult allOrder(@RequestBody Orders orders,Integer page,Integer limit){
        ApiResult<Object> result = new ApiResult<>();
        try {
            PageInfo<Orders> pageInfo=orderService.findOrders(orders,page,limit);
            result.setMessage("订单查看成功");
            result.setData(pageInfo);
        } catch (SuperMarketException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(Constants.RESP_STATUS_BADREQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "修改订单为已发货",notes = "修改订单",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateOrder")
    public ApiResult updateOrder(@RequestBody Orders orders){
        ApiResult<Object> result = new ApiResult<>();
        if (orders.getStatus()!=3){
            result.setMessage("只能修改订单状态为已发货");
        }
        orderService.modifyOrders(orders);
        return result;
    }
}
