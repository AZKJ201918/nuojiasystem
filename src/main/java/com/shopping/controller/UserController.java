package com.shopping.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.commons.resp.ApiResult;
import com.shopping.config.shiro.WebSessionManager;
import com.shopping.entity.User;
import com.shopping.service.UserService;
import com.shopping.util.MD5Util;
import com.shopping.util.MD5Util1;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@Api(value = "用户管理模块")
public class UserController {
    @Autowired
    private UserService userService;
    @ApiOperation(value = "查看所有用户",notes = "查看所有用户",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/loadUser")
    @RequiresRoles("root")
    public ApiResult loadUser(Integer page,Integer limit,String name){
        ApiResult<Object> result = new ApiResult<>();
        try {
            PageInfo<User> userList=userService.findUser(page,limit,name);
            result.setMessage("查看用户成功");
            result.setData(userList);
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
    @ApiOperation(value = "新增用户",notes = "新增用户",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/insertUser")
    @RequiresRoles("root")
    public ApiResult insertUser(User user){
        ApiResult<Object> result = new ApiResult<>();
        try {
            if (user.getRid()==1){
                result.setMessage("不能新增角色为系统管理员");
                result.setCode(Constants.RESP_STATUS_BADREQUEST);
                return result;
            }
            if (user.getName()!=null){
                boolean flag=userService.findUserExsits(user.getName());
                if (flag){
                    result.setMessage("用户名已存在");
                    result.setCode(Constants.RESP_STATUS_BADREQUEST);
                    return result;
                }
            }
            userService.addUser(user);
            result.setMessage("新增用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "修改用户",notes = "修改用户",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/updateUser")
    @RequiresRoles("root")
    public ApiResult updateUser(User user){
        ApiResult<Object> result = new ApiResult<>();
        try {
            Integer myRid=userService.findMyRid(user.getUid());
            if (myRid!=1){
                if (user.getRid()==1){
                    result.setMessage("不能修改角色为系统管理员");
                    result.setCode(Constants.RESP_STATUS_BADREQUEST);
                    return result;
                }
            }
            if (user.getName()!=null){
                boolean flag=userService.findUserExsits1(user.getName(),user.getUid());
                if (flag){
                    result.setMessage("用户名已存在");
                    result.setCode(Constants.RESP_STATUS_BADREQUEST);
                    return result;
                }
            }
            userService.modifyUser(user);
            result.setMessage("修改用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "删除用户",notes = "删除用户",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/deleteUser")
    @RequiresRoles("root")
    public ApiResult deleteUser(User user){
        ApiResult<Object> result = new ApiResult<>();
        try {
            userService.removeUser(user);
            result.setMessage("删除用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看用户名是否存在",notes = "查看用户名是否存在，防止相同的用户出现,true表示用户名已存在",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("/userExsits")
    @RequiresRoles("root")
    public ApiResult userExsits(String name){
        ApiResult<Object> result = new ApiResult<>();
        try {
            boolean flag=userService.findUserExsits(name);
            result.setMessage("查看用户名是否存在成功");
            result.setData(flag);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("后台服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "登录",notes = "登录",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("login")
    public ApiResult login(@RequestBody User user,
                                  HttpServletRequest request)  {
        HashMap<String, String> map = new HashMap<>();
        ApiResult<Object> result = new ApiResult<>();
        Subject subject = SecurityUtils.getSubject();
        // 尝试登录
        try {
            UsernamePasswordToken authenticationToken = new UsernamePasswordToken(user.getName(), MD5Util1.getMD5(user.getPassword()));
            subject.login(authenticationToken);
            // 从request中获取token,完成响应
            map.put(WebSessionManager.TOKEN_NAME,
                    (String) request.getAttribute(WebSessionManager.TOKEN_NAME));
            result.setMessage("登录成功");
            result.setData(map);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            result.setMessage("用户不存在");
            result.setCode(Constants.RESP_STATUS_BADREQUEST);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            result.setMessage("密码不正确");
            result.setCode(Constants.RESP_STATUS_BADREQUEST);
        }
        return result;
    }
    @ApiOperation(value = "查看当前用户用户的角色",notes = "查看角色",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("role")
    public ApiResult role(){
        ApiResult<Object> result = new ApiResult<>();
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(true);
            Object roles = session.getAttribute("roles");
            result.setData(roles);
            result.setMessage("查看用户角色成功");
        } catch (InvalidSessionException e) {
            e.printStackTrace();
            result.setMessage("服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
    @ApiOperation(value = "查看所有用户的角色",notes = "查看所有角色",httpMethod = "POST")
    @ApiImplicitParam
    @PostMapping("allRole")
    public ApiResult allRole(){
        ApiResult<Object> result = new ApiResult<>();
        try {
            List<User> roles=userService.FindAllRoles();
            result.setData(roles);
            result.setMessage("查看用户角色成功");
        } catch (InvalidSessionException e) {
            e.printStackTrace();
            result.setMessage("服务器异常");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;
    }
}
