package com.shopping.controller;

import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.DeatilBanner;
import com.shopping.service.DetailService;
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
@Api(value = "商品详情管理模块")
public class DetailController {
    @Autowired
    private DetailService detailService;
    @ApiOperation(value = "查看某个商品详情的轮播图",notes = "查看某个商品详情的轮播图，此处传商品id",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadDetailBanner ")
    public ApiResult loadDetailBanner(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        List<DeatilBanner> deatilBannerList= null;
        try {
            deatilBannerList = detailService.findDetailBanner(id);
            result.setData(deatilBannerList);
            result.setMessage("查看轮播图成功");
        } catch (SuperMarketException e) {
            e.printStackTrace();
            result.setCode(Constants.RESP_STATUS_BADREQUEST);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("后台服务器异常");
        }
        return result;
    }
    @ApiOperation(value = "修改某个商品详情的轮播图",notes = "修改某个商品详情的轮播图",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateDetailBanner")
    public ApiResult updateDetailBanner(@RequestBody DeatilBanner deatilBanner){
        ApiResult<Object> result = new ApiResult<>();
        try {
            detailService.modifyDetailBanner(deatilBanner);
            result.setMessage("修改商品详情轮播图成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "删除某个商品详情的轮播图",notes = "删除某个商品详情的轮播图",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteDetailBanner")
    public ApiResult deleteDetailBanner(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        try {
            detailService.removeDetailBanner(id);
            result.setMessage("删除商品详情轮播图成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "新增某个商品详情的轮播图",notes = "新增某个商品详情的轮播图",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertDetailBanner")
    public ApiResult insertDetailBanner(@RequestBody DeatilBanner deatilBanner){
        ApiResult<Object> result = new ApiResult<>();
        try {
            detailService.addDetailBanner(deatilBanner);
            result.setMessage("新增商品详情轮播图成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
