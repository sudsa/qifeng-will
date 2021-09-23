package com.qifeng.will.userserver.cotroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
@Slf4j
public class PermissionController {

    @RequestMapping("checkPermission")
    public String checkPermission(@RequestParam("userId") String userId,
                                  @RequestParam("permissionUrl") String permissionUrl){
        log.info("userId = {}",userId);
        log.info("permissionUrl = {}",permissionUrl);
        return "checkPermission success";
    }
}
