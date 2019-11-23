package com.shopping.config.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.commons.constans.Constants;
import com.shopping.commons.resp.ApiResult;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 自定义过滤器，对option直接放行，对未登录响应json
 * @author: asen
 * creat time: 2019-08-09 23:07
 * @description: shiro默认对option拦截导致跨域时无法完成预见，所以对全部option放行。
 * 默认请求shiro未登录是重定向到登录页面，在跨域时会报错，所以覆盖onAccessDenied方法手动针对未登录的情况处理
 */
public class MyAuthenticationFilter extends FormAuthenticationFilter {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    /**
     * 对cors预检请求放行处理
     */
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return super.onPreHandle(request, response, mappedValue);
    }

    /**
     * 未登录的情况，手动处理响应json
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse =(HttpServletResponse)response;
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        httpServletResponse.setContentType("text/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Vary","Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
        PrintWriter writer = httpServletResponse.getWriter();
        ApiResult<Object> result = new ApiResult<>();
        result.setCode(Constants.RESP_STATUS_NOAUTH);
        result.setMessage("用户未登录");
        String s = objectMapper.writeValueAsString(result);
        writer.write(s);
        writer.flush();
        return false;
    }
}
