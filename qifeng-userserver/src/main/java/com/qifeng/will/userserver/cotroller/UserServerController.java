package com.qifeng.will.userserver.cotroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/app/")
@Slf4j
public class UserServerController {

    @RequestMapping("validateApp")
    public String verifyApp(@RequestParam(value = "appId") String appId,
                                  @RequestParam(value= "appKey") String appKey){

        log.info("appId = {}",appId);
        log.info("appKey = {}",appKey);

        return "verifyApp";
    }

}
