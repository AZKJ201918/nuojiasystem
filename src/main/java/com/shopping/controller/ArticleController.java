package com.shopping.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.entity.Article;
import com.shopping.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "公众号文章管理模块")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @ApiOperation(value = "查看公众号文章",notes = "查看公众号文章",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadArticle")
    public ApiResult loadArticle(@RequestBody Article article,Integer page,Integer limit){
        ApiResult<Object> result = new ApiResult<>();
        try {
            PageInfo<Article> pageInfo=articleService.findArticle(article,page,limit);
            result.setMessage("查看公众号成功");
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
    @ApiOperation(value = "修改公众号文章",notes = "修改公众号文章",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateArticle")
    public ApiResult updateArticle(@RequestBody Article article){
        ApiResult<Object> result = new ApiResult<>();
        try {
            articleService.modifyArticle(article);
            result.setMessage("修改文章成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "删除公众号文章",notes = "删除公众号文章",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteArticle")
    public ApiResult deleteArticle(Integer id){
        ApiResult<Object> result = new ApiResult<>();
        try {
            articleService.removeArticle(id);
            result.setMessage("删除文章成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "新增公众号文章",notes = "新增公众号文章",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertArticle")
    public ApiResult insertArticle(@RequestBody Article article){
        ApiResult<Object> result = new ApiResult<>();
        try {
            articleService.addArticle(article);
            result.setMessage("新增文章成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
