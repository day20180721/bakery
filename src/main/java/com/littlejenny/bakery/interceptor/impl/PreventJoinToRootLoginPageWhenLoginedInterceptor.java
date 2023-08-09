package com.littlejenny.bakery.interceptor.impl;

import com.littlejenny.bakery.pojo.RootUser;
import com.littlejenny.bakery.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class PreventJoinToRootLoginPageWhenLoginedInterceptor implements HandlerInterceptor {
    @Value("${business-prefix-value}")
    private String buisnessPrefix;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        RootUser rootUser = RequestUtil.getRootUserOrNull(request);
        if (rootUser != null) {
            String redirectUrl = buisnessPrefix + "/b-shop";
            response.sendRedirect(redirectUrl);
            return false;
        }
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
