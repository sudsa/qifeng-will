package com.hanxiaozhang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gate")
public class GatewayController {


    @RequestMapping("timeRoute")
    public String test1(){
        System.out.println("here");
        return "timeRoute";
    }

}
