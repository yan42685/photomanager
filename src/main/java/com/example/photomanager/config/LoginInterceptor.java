package com.example.photomanager.config;

import com.example.photomanager.common.KnownException;
import com.example.photomanager.enums.ExceptionEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 小朝
 * @date 2020/5/7
 **/
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        // 包含以下字符串，不进行拦截
        boolean isInterceptor = url.contains("login") || url.contains("register") || url.contains("check") || url.contains("send");
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated() && !isInterceptor) {
            // 未登录或者不是以上请求，则抛出异常
            throw new KnownException(ExceptionEnum.NOT_LOGIN);
        }
        // 放行
        return true;
    }
}
