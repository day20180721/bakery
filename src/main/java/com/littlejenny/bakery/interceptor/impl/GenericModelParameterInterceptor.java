package com.littlejenny.bakery.interceptor.impl;

import com.littlejenny.bakery.utils.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GenericModelParameterInterceptor implements HandlerInterceptor {
    public static final String SELECTED_DATE = "selectedDate";
    public static final String TODAY = "today";
    public static final String BUSINESS_REQUEST_PREFIX = "businessPrefix";
    public static final String CUSTOMER_REQUEST_PREFIX = "customerPrefix";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        setToday(request);
        setSelectedDate(request);
        setBusinessRequestPrefix(request);
        setCustomerRequestPrefix(request);
        return true;
    }

    private void setToday(HttpServletRequest request) {
        String today = DateUtil.getMySqlCURDATEInGMT8();
        request.setAttribute(TODAY, today);
    }

    private void setSelectedDate(HttpServletRequest request) {
        String today = DateUtil.getMySqlCURDATEInGMT8();
        String selectedDate = request.getParameter(SELECTED_DATE);
        if (selectedDate == null || selectedDate.isEmpty()) {
            request.setAttribute(SELECTED_DATE, today);
        } else {
            request.setAttribute(SELECTED_DATE, selectedDate);
        }
    }

    @Value("${business-prefix-value}")
    private String businessPrefixValue;

    private void setBusinessRequestPrefix(HttpServletRequest request) {
        request.setAttribute(BUSINESS_REQUEST_PREFIX, businessPrefixValue);
    }

    @Value("${customer-prefix-value}")
    private String customerPrefixValue;

    private void setCustomerRequestPrefix(HttpServletRequest request) {
        request.setAttribute(CUSTOMER_REQUEST_PREFIX, customerPrefixValue);
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
