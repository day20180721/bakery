package com.littlejenny.bakery.interceptor.impl;

import com.littlejenny.bakery.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Slf4j
@Component
public class LoginInterceptorImpl implements HandlerInterceptor {
    public static String USER_KEY = "user";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        AuthUser user = RequestUtil.getUserOrNull(request);
        if(user == null){
            String redirectUrl = "/oauth";
            response.sendRedirect(redirectUrl);
            return false;
        }
        log.debug("user " + user.getNickname() + " 請求 " + request.getRequestURI());
        request.setAttribute(USER_KEY,user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }
}
