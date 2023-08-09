package com.littlejenny.bakery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class IndexController {

    @GetMapping("/info")
    public String info() {

        return "info";
    }
    @GetMapping("/error")
    public String error() {

        return "error";
    }
    @GetMapping("/privacy")
    public String privacy() {

        return "privacy";
    }
}
