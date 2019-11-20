package com.shopping.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.Commodity;
import com.shopping.entity.Link;
import com.shopping.entity.Options;
import com.shopping.service.OptionService;
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
@Api(value = "按钮管理模块")
public class OptionController {
    @Autowired
    private OptionService optionService;
    @ApiOperation(value = "查看全部按钮",notes = "查看",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/allOption")
    public ApiResult allOption(){
        ApiResult<Object> result = new ApiResult<>();
        try {
            List<Options> optionsList=optionService.findAllOption();
            result.setMessage("查看按钮成功");
            result.setData(optionsList);
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
    @ApiOperation(value = "新增按钮",notes = "新增按钮",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertOption")
    public ApiResult insertOption(@RequestBody Options options){
        ApiResult<Object> result = new ApiResult<>();
        try {
            optionService.addOption(options);
            result.setMessage("新增按钮成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "修改按钮",notes = "修改按钮",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateOption")
    public ApiResult updateOption(@RequestBody Options options){
        ApiResult<Object> result = new ApiResult<>();
        try {
            optionService.modifyOption(options);
            result.setMessage("修改按钮成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "删除按钮",notes = "删除按钮",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteOption")
    public ApiResult deleteOption(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        try {
            optionService.removeOption(id);
            result.setMessage("删除按钮成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看全部内链",notes = "查看全部内链",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("/selectLink")
    public ApiResult selectLink(){
        ApiResult<Object> result = new ApiResult<>();
        try {
            List<Link> linkList=optionService.findAllLink();
            result.setMessage("查看内链成功");
            result.setData(linkList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "模糊查询商品",notes = "模糊查询商品",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/likeCommodity")
    public ApiResult likeCommodity(String name,Integer page,Integer limit){
        ApiResult<Object> result = new ApiResult<>();
        try {
            PageInfo pageInfo=optionService.findLikeCommodity(name,page,limit);
            result.setMessage("查看商品成功");
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
