package com.shopping.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.VolumeAndMoney;
import com.shopping.service.CensusService;
import com.shopping.util.UrlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "统计管理模块")
public class CensusController {
    @Autowired
    private CensusService censusService;
    @ApiOperation(value = "近七天销售各商品销售额和销量以及总销售额和销量",notes = "近七天销售各商品销售额和销量以及总销售额和销量",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/census")
    @RequiresRoles(value={"root","admin","orders"},logical= Logical.OR)
    public ApiResult census(Integer id){
        ApiResult< List<Map<Object, Object>> > result = new ApiResult<>();
        try {
            List<Map<Object, Object>> volumeAndMoneyList=censusService.findVolumeAndMoney(id);
            result.setMessage("查看成功");
            result.setData(volumeAndMoneyList);
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
    @ApiOperation(value = "近七天访问量和注册用户量",notes = "近七天访问量和注册用户量",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/visitAndRegister")
    @RequiresRoles(value={"root","admin","orders"},logical= Logical.OR)
    public ApiResult visitAndRegister(){
        ApiResult<Object> result = new ApiResult<>();
        try {
            List<Map<String,Object>> visitAndRegisterList=censusService.findVisitAndRegister();
            String url="http://localhost:555/todayVisit";
            JSONObject jsonObject = UrlUtils.doPostJson(url);
            Integer registerCount = jsonObject.getInteger("registerCount");
            Integer visitCount = jsonObject.getInteger("visitCount");
            HashMap<String, Object> map = new HashMap<>();
            map.put("register",registerCount);
            map.put("visit",visitCount);
            map.put("click_date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            visitAndRegisterList.add(map);
            result.setMessage("查看成功");
            result.setData(visitAndRegisterList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
