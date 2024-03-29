package com.shopping.service;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.User;

import java.util.List;

public interface UserService {
    PageInfo<User> findUser(Integer page, Integer limit, String name) throws SuperMarketException;

    void addUser(User user);

    void modifyUser(User user);

    void removeUser(User user);

    boolean findUserExsits(String name);

    List<User> FindAllRoles();

    boolean findUserExsits1(String name, Integer uid);

    Integer findMyRid(Integer uid);
}
