package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.pojo.R;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class TestingController {
    @GetMapping({"","/"})
    public String index(){

        return "test";
    }
    @GetMapping("/redirect")
    public ModelAndView redirect(ModelMap model){
        String url ="http://day20180721.ddns.net/business/b-shop";
        return new ModelAndView("redirect:" + url); // 重新導向到指定的url
    }
    @ResponseBody
    @GetMapping("/error")
    public R postError() {
        int i = 1 / 0;
        return R.error;
    }
    @ResponseBody
    @PostMapping("/error")
    public R getError() {
        throw new RuntimeException("Good");
    }
}
