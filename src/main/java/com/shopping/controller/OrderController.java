package com.shopping.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.Orders;
import com.shopping.entity.PrintOrder;
import com.shopping.service.OrderService;
import com.shopping.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    @RequiresRoles(value={"root","admin","orders"},logical= Logical.OR)
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
    @ApiOperation(value = "修改订单为已发货",notes = "根据订单的orderid修改",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateOrder")
    @RequiresRoles(value={"root","admin","orders"},logical= Logical.OR)
    public ApiResult updateOrder(@RequestBody Orders orders){
        ApiResult<Object> result = null;
        try {
            result = new ApiResult<>();
            if (orders.getStatus()!=3){
                result.setMessage("只能修改订单状态为已发货");
                result.setCode(Constants.RESP_STATUS_BADREQUEST);
                return result;
            }
            orderService.modifyOrders(orders);
        } catch (SuperMarketException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(Constants.RESP_STATUS_BADREQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "导出订单",notes = "导出订单",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/importOrder")
    //@RequiresRoles(value={"root","admin","orders"},logical= Logical.OR)
    public void importOrder(HttpServletResponse response){
        try {
            List<PrintOrder> ordersList=orderService.findDaiSendOrder();
            LinkedHashMap fieldMap = new LinkedHashMap();
            String sheetName = "订单信息表";
            fieldMap.put("orderid", "订单号");
            fieldMap.put("finalprice", "成交价格");
            fieldMap.put("cname","商品名称");
            fieldMap.put("subname","商品副标题");
            fieldMap.put("num","商品数量");
            fieldMap.put("name","收件人姓名");
            fieldMap.put("phone","收件人联系方式");
            fieldMap.put("province","省份");
            fieldMap.put("city","城市");
            fieldMap.put("area","区/县");
            fieldMap.put("detail","详细地址");
            ExcelUtil.listToExcel(ordersList, fieldMap, sheetName, response);
        } catch (SuperMarketException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
