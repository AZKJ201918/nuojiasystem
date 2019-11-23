package com.shopping.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.Commodity;
import com.shopping.service.CommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "商品管理模块")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;
    @ApiOperation(value = "查看全部商品",notes = "支持模糊查询",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/allCommodity")
    @RequiresRoles(value={"root","admin","orders"},logical= Logical.OR)
    public ApiResult allCommodity(String name,Integer page,Integer limit){
        ApiResult<Object> result = new ApiResult<>();
        try {
            PageInfo<Commodity> pageInfo = commodityService.findCommodityAttribute(name, page, limit);
            result.setMessage("查看商品成功");
            result.setData(pageInfo);
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
    @ApiOperation(value = "修改商品",notes = "修改商品",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateCommodity")
    @RequiresRoles(value={"root","admin"},logical= Logical.OR)
    @Transactional(rollbackFor = Exception.class)
    public ApiResult updateCommodity(@RequestBody Commodity commodity){
        ApiResult<Object> result = new ApiResult<>();
        try {
            commodityService.modifyCommodityAttribute(commodity);
            result.setMessage("修改商品成功");
        }catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
    @ApiOperation(value = "新增商品",notes = "新增商品",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertCommodity")
    @RequiresRoles(value={"root","admin"},logical= Logical.OR)
    @Transactional(rollbackFor = Exception.class)
    public ApiResult insertCommodity(@RequestBody Commodity commodity){
        ApiResult<Object> result = new ApiResult<>();
        try {
            commodityService.addCommodityAttribute(commodity);
            result.setMessage("新增商品成功");
        }catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
    @ApiOperation(value = "删除商品",notes = "删除商品",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteCommodity")
    @RequiresRoles(value={"root","admin"},logical= Logical.OR)
    @Transactional(rollbackFor = Exception.class)
    public ApiResult deleteCommodity(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        try {
            commodityService.removeCommodityAttribute(id);
            result.setMessage("删除商品成功");
        }catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
