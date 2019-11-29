package com.shopping.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.Discuss;
import com.shopping.entity.VolumeAndMoney;
import com.shopping.service.CensusService;
import com.shopping.util.UrlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private MongoTemplate mongoTemplate;
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
    @ApiOperation(value = "查看商品评论",notes = "商品评论",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadDiscuss")
    public ApiResult loadDiscuss(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "5")Integer limit,Integer id,Integer evaluate){
        ApiResult<Object> result = new ApiResult<>();
        Query query = new Query();
        if (id!=null){
            query.addCriteria(Criteria.where("cid").is(id));
        }
        if (evaluate!=null){
            query.addCriteria(Criteria.where("evaluate").is(evaluate));
        }
        List<Discuss> discusses = null;
        try {
            long count = mongoTemplate.count(query, Discuss.class);
            query.skip((page-1)*limit).limit(limit);
            discusses = mongoTemplate.find(query, Discuss.class);
            HashMap<Object, Object> map = new HashMap<>();
            map.put("discusses",discusses);
            map.put("count",count);
            System.out.println(discusses);
            result.setMessage("查看评论成功");
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
