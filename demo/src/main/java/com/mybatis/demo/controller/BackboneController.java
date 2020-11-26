package com.mybatis.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BackboneController {

    @GetMapping("/home")
    public String getBackbonePage(){
        return "backbone";
    }
}
