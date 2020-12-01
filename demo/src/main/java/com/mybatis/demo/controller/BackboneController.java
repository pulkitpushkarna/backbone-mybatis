package com.mybatis.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BackboneController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

//    @GetMapping("/home")
//    public String getBackbonePage(){
//        return "backbone";
//    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
