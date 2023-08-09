package com.littlejenny.bakery.utils;

import com.littlejenny.bakery.interceptor.impl.GenericModelParameterInterceptor;
import com.littlejenny.bakery.pojo.RootUser;
import jakarta.servlet.http.HttpServletRequest;
import me.zhyd.oauth.model.AuthUser;

import java.text.ParseException;
import java.util.Date;

public class RequestUtil {
    public static final String USER = "user";
    public static final String ROOT_USER = "root-user";

    public static String getSelectedStringDate(HttpServletRequest req) {
        return (String) req.getAttribute(GenericModelParameterInterceptor.SELECTED_DATE);
    }
    public static Date getSelectedDate(HttpServletRequest req) throws ParseException {
        return DateUtil.getDateFromStringInGMT8(getSelectedStringDate(req));
    }
    public static AuthUser getUserOrNull(HttpServletRequest req) {
        return (AuthUser) req.getSession().getAttribute(USER);
    }

    public static RootUser getRootUserOrNull(HttpServletRequest req) {
        return (RootUser) req.getSession().getAttribute(ROOT_USER);
    }
}
