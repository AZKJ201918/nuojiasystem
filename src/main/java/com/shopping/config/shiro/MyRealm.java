package com.shopping.config.shiro;

import com.shopping.entity.User;
import com.shopping.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author: liu
 * creat time: 2019-11-08 20:17
 * @description:
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;

    /**
     * 访问鉴权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //取出userId 从 缓存中获取权限集合
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        Integer userId = (Integer) principals.getPrimaryPrincipal();
        List<String> roles = (List<String>) session.getAttribute("roles");
        Set<String> rolesSet = new HashSet<>();
        roles.forEach(rolesSet::add);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(rolesSet);
        return info;
    }

    /**
     * 登录认证
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        User user = userMapper.selectName(username);
        //账号不存在
        if (user == null) {
            throw new UnknownAccountException();
        }
        //密码错误
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException();
        }

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        //session.setAttribute("user",user);
        //List<String> permsList = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        roles=userMapper.selectRoles(user);
        session.setAttribute("roles",roles);
        return new SimpleAuthenticationInfo(user.getUid(), password, getName());
    }
}
