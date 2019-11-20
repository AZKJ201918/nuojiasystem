package com.shopping.controller;

import com.github.pagehelper.Constant;
import com.github.pagehelper.PageInfo;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.Activity;
import com.shopping.entity.Commercial;
import com.shopping.service.ActiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "商品活动管理模块")
public class ActiveController {
    @Autowired
    private ActiveService activeService;
   /* @ApiOperation(value = "查看所有的活动",notes = "查看所有的活动",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/loadActive")
    public ApiResult loadActive(){
        ApiResult<Object> result = new ApiResult<>();
        try {
            List<Activity> activeList=activeService.findActive();
            result.setMessage("查看活动成功");
            result.setData(activeList);
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
    @ApiOperation(value = "修改活动",notes = "修改活动",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateActive")
    public ApiResult updateActive(Activity activity){
        ApiResult<Object> result = new ApiResult<>();
        try {
            activeService.modifyActive(activity);
            result.setMessage("修改活动成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }*/
   @ApiOperation(value = "查看某个商品的活动",notes = "查看某个的活动,此处是商品id",httpMethod = "POST")
   @ApiImplicitParam
   @PostMapping("/loadActive")
   public ApiResult loadActive(Integer id){
       ApiResult<Object> result = new ApiResult<>();
       try {
           Commercial commercial=activeService.findActivity(id);
           result.setMessage("查看商品活动成功");
           result.setData(commercial);
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
    @ApiOperation(value = "修改某个商品的活动",notes = "修改某个的活动",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateActive")
    public ApiResult updateActive(@RequestBody Commercial commercial){
        ApiResult<Object> result = new ApiResult<>();
        try {
            activeService.modifyActivity(commercial);
            result.setMessage("修改商品活动成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "新增某个商品的活动",notes = "新增某个的活动",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertActive")
    public ApiResult insertActive(@RequestBody Commercial commercial){
        ApiResult<Object> result = new ApiResult<>();
        try {
            activeService.addActivity(commercial);
            result.setMessage("新增商品活动成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "删除某个商品的活动",notes = "删除某个商品的活动",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteActive")
    public ApiResult deleteActive(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        try {
            activeService.removeActivity(id);
            result.setMessage("删除商品活动成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
