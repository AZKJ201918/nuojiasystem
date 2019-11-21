package com.shopping.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.Intergral;
import com.shopping.entity.Video;
import com.shopping.entity.WholeRetail;
import com.shopping.service.WholeRetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "全局分销管理模块")
public class WholeRetailController {
    @Autowired
    private WholeRetailService wholeRetailService;
    @ApiOperation(value = "查看全局分销",notes = "查看",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/wholeRetail")
    public ApiResult wholeRetail(){
        ApiResult<Object> result = new ApiResult<>();
        try {
            WholeRetail wholeRetail=wholeRetailService.findWholeRetail();
            result.setData(wholeRetail);
            result.setMessage("全局分销查看成功");
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
    @ApiOperation(value = "修改全局分销",notes = "修改",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateWholeRetail")
    public ApiResult updateWholeRetail(WholeRetail wholeRetail){
        ApiResult<Object> result = new ApiResult<>();
        try {
            wholeRetailService.modifyWholeRetail(wholeRetail);
            result.setMessage("修改全局分销成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看签到可以获得的积分",notes = "查看",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/loadIntegral")
    public ApiResult loadIntegral(){
        ApiResult<Object> result = new ApiResult<>();
        try {
            Integer integral=wholeRetailService.findIntegral();
            result.setMessage("查看签到可以获得的积分成功");
            result.setData(integral);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "修改签到可以获得的积分",notes = "修改",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateIntegral")
    public ApiResult updateIntegral(Intergral intergral){
        ApiResult<Object> result = new ApiResult<>();
        try {
            wholeRetailService.modifyIntegral(intergral);
            result.setMessage("修改签到可以获得的积分成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
