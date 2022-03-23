package com.cy.store.interceptor;

/**
 * @author XuBowen
 * @version 1.0
 * @date 2022/3/22 23:37
 */

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 定义处理器拦截器 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 检测全局session对象中是否有uid数据，如果有则放行，如果没有重定向到登陆界面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器(url+Controller：映射)
     * @return 如果返回值为true表示放行当前请求，为false表示拦截当前请求
     * @throws Exception 抛出异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("uid") == null) {
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}
