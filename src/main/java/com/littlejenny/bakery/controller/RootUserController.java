package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.pojo.RootUser;
import com.littlejenny.bakery.to.RootLoginTO;
import com.littlejenny.bakery.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/oauth/root")
public class RootUserController {
    @Value("${user-root-account}")
    private String account;
    @Value("${user-root-password}")
    private String password;
    @Value("${business-prefix-value}")
    private String businessPrefix;

    @GetMapping({"", "/"})
    public String index() {
        return "root-login";
    }

    @PostMapping({"", "/"})
    public String login(@RequestParam("account") @NotBlank String _account, @RequestParam("password") @NotBlank String _password, HttpServletRequest req) throws IOException {
        String redirectUrl = "";
        if (_account.equals(account) && _password.equals(password)){
            req.getSession().setAttribute(RequestUtil.ROOT_USER, new RootUser());
            redirectUrl = businessPrefix + "/b-shop";
            return "redirect:" + redirectUrl;
        }
        redirectUrl = "/oauth/root";
        return "redirect:" + redirectUrl;
    }
}
