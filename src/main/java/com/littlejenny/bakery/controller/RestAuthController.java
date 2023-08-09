package com.littlejenny.bakery.controller;

import com.alibaba.fastjson.JSONObject;
import com.littlejenny.bakery.enums.JustAuthPlatformInfo;
import com.littlejenny.bakery.interceptor.impl.LoginInterceptorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.*;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthScopeUtils;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/2/19 9:28
 * @since 1.8
 */
@Slf4j
@Controller
@RequestMapping("/oauth")
public class RestAuthController {

    @RequestMapping("")
    public String index(Model model) {
        addEnableAuthPlatformListToModel(model);
        return "login-index";
    }

    private void addEnableAuthPlatformListToModel(Model model) {
        model.addAttribute("enableAuthPlatforms", JustAuthPlatformInfo.getPlatformInfos());
    }

    /**
     * 返回對平台請求grant的路徑
     *
     * @param source   平台名稱，如google
     * @param response
     * @throws IOException
     */
    @RequestMapping("/render/{source}")
    @ResponseBody
    public void renderAuth(@PathVariable("source") String source, HttpServletResponse response, HttpSession session) throws IOException {
        log.info("進入render：" + source);
        AuthRequest authRequest = getAuthRequest(source);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        log.info("AuthorizeUrl : " + authorizeUrl);
        response.sendRedirect(authorizeUrl);

    }

    /**
     * oauth平台中配置的授权回调地址，以本项目为例，在创建github授权应用时的回调地址应为：http://127.0.0.1:8443/oauth/callback/github
     */
    @RequestMapping("/callback/{source}")
    public String login(@PathVariable("source") String source, AuthCallback callback, Model model, HttpSession session) {
        log.info("進入callback：" + source + " callback params：" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = getAuthRequest(source);
        AuthResponse<AuthUser> response = authRequest.login(callback);
        log.info("AuthResponse " + JSONObject.toJSONString(response));

        if (response.ok()) {
            session.setAttribute(LoginInterceptorImpl.USER_KEY, response.getData());
            return "redirect:/shop";
        }
        addAuthResponseErrorToModel(model, response);
        return "login-error";
    }

    private void addAuthResponseErrorToModel(Model model, AuthResponse<AuthUser> response) {
        model.addAttribute("errorMsg", response.getMsg());
    }

    @Value("${oauth2.google.clientId}")
    private String googleClientId;
    @Value("${oauth2.google.clientSecret}")
    private String googleClientSecret;
    @Value("${oauth2.google.redirectUri}")
    private String googleRedirectUri;

    private AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source.toLowerCase()) {
            case "google":
                authRequest = new AuthGoogleRequest(AuthConfig.builder()
                        .clientId(googleClientId)
                        .clientSecret(googleClientSecret)
                        .redirectUri(googleRedirectUri)
                        .scopes(AuthScopeUtils.getScopes(AuthGoogleScope.USER_EMAIL, AuthGoogleScope.USER_PROFILE, AuthGoogleScope.USER_OPENID))
                        .build());
                break;
            default:
                break;
        }
        if (null == authRequest) {
            throw new AuthException("未获取到有效的Auth配置");
        }
        return authRequest;
    }
}
