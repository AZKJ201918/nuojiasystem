package com.shopping.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.Video;
import com.shopping.service.VideoService;
import com.shopping.util.QiniuFileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "视频管理模块")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @ApiOperation(value = "查看全部视频",notes = "查看",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/allVideo")
    public ApiResult allVideo(Integer page,Integer limit){
        ApiResult<Object> result = new ApiResult<>();
        try {
            PageInfo<Video> pageInfo=videoService.findAllVideo(page,limit);
            result.setData(pageInfo);
            result.setMessage("视频查看成功");
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
    @ApiOperation(value = "新增视频",notes = "新增",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertVideo")
    public ApiResult insertVideo(@RequestBody Video video){
        ApiResult<Object> result = new ApiResult<>();
        try {
            videoService.addVideo(video);
            result.setMessage("新增视频成功");
        }catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "七牛云上传视频",notes = "上传",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/uploadVideo")
    public ApiResult uploadVideo(MultipartFile file){
        ApiResult<Object> result = new ApiResult<>();
        try {
            String videoUrl = QiniuFileUploadUtil.uploadVideo(file);
            result.setMessage("视频上传成功");
            result.setData(videoUrl);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "删除视频",notes = "删除",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteVideo")
    public ApiResult insertVideo(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        try {
            videoService.removeVideo(id);
            result.setMessage("视频删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "修改视频",notes = "修改",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateVideo")
    public ApiResult updateVideo(@RequestBody Video video){
        ApiResult<Object> result = new ApiResult<>();
        try {
            videoService.modifyVideo(video);
            result.setMessage("视频修改成功");
        }catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
