package com.shopping.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.SlideShow;
import com.shopping.service.SlideShowService;
import com.shopping.util.QiniuFileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "首页轮播管理模块")
public class SlideShowController {
    @Autowired
    private SlideShowService slideShowService;
    @ApiOperation(value = "查看轮播图",notes = "查看轮播图",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/slideShow")
    @RequiresRoles(value={"root","admin","orders"},logical= Logical.OR)
    public ApiResult slideShow(Integer page,Integer limit){
        ApiResult<Object> result = new ApiResult<>();
        PageInfo<SlideShow> pageInfo= null;
        try {
            pageInfo = slideShowService.loadSlideShow(page,limit);
        } catch (SuperMarketException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setCode(Constants.RESP_STATUS_BADREQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        result.setData(pageInfo);
        return result;
    }
    @ApiOperation(value = "上传轮播图图片",notes = "上传轮播图片",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/slideShowImg")
    @RequiresRoles(value={"root","admin"},logical= Logical.OR)
    public ApiResult discussImg(MultipartFile file){
        ApiResult<Object> result = new ApiResult<>();
        try {
            String detailsImg = QiniuFileUploadUtil.uploadHeadImg(file);
            result.setMessage("文件上传成功");
            result.setData(detailsImg);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "新增轮播图",notes = "新增轮播图",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertSlideShow")
    @RequiresRoles(value={"root","admin"},logical= Logical.OR)
    public ApiResult insertSlideShow(@RequestBody SlideShow slideShow){
        ApiResult<Object> result = new ApiResult<>();
        try {
            slideShowService.addSlideShow(slideShow);
            result.setMessage("轮播图新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setData(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "修改轮播图",notes = "修改轮播图",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateSlideShow")
    @RequiresRoles(value={"root","admin"},logical= Logical.OR)
    public ApiResult updateSlideShow(@RequestBody SlideShow slideShow){
        ApiResult<Object> result = new ApiResult<>();
        try {
            slideShowService.modifySlideShow(slideShow);
            result.setMessage("修改轮播图成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "删除轮播图",notes = "删除轮播图",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteSlideShow")
    @RequiresRoles(value={"root","admin"},logical= Logical.OR)
    public ApiResult deleteSlideShow(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        try {
            slideShowService.removeSlideShow(id);
            result.setMessage("删除轮播图成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
