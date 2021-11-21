package com.qifeng.will.qifengwebflux.controller;

import com.qifeng.will.qifengwebflux.config.ReqMappigRestController;
import org.springframework.web.bind.annotation.RequestMapping;

@ReqMappigRestController("path")
public class PathMappingRestController {

    @RequestMapping("an")
    public String path(){
        return "annotionMerge";
    }

}
