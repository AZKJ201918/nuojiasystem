package com.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.User;
import com.shopping.mapper.UserMapper;
import com.shopping.service.UserService;
import com.shopping.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageInfo<User> findUser(Integer page, Integer limit, String name) throws SuperMarketException {
        PageHelper.startPage(page,limit);
        List<User> userList = userMapper.selectUser(name);
        if (userList==null){
            throw new SuperMarketException("没有管理员信息");
        }
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public void addUser(User user) {
        userMapper.insertUser(user);
        String password = user.getPassword();
        if (password!=null){
           /* password= MD5Util.encrypt(password);*/
            user.setPassword(password);
        }
        if (user.getRid()!=null){
            userMapper.insertUser_role(user);
        }
    }

    @Override
    public void modifyUser(User user) {
        String password = user.getPassword();
        if (password!=null){
           /* password=MD5Util.encrypt(password);*/
            user.setPassword(password);
        }
        userMapper.updateUser(user);
        if (user.getUid()!=null&&user.getRid()!=null){
            userMapper.updateUser_role(user);
        }
    }

    @Override
    public void removeUser(User user) {
        userMapper.deleteUser(user);
        if (user.getId()!=null){
            userMapper.deleteUser_role(user);
        }
    }

    @Override
    public boolean findUserExsits(String name) {
        Integer count=userMapper.selectUserExists(name);
        if (count>=1){
            return true;
        }
        return false;
    }

    @Override
    public List<User> FindAllRoles() {
        return userMapper.SelectAllRoles();
    }
}
