package com.qifeng.will.canal.controller;

import com.qifeng.will.canal.service.RedisCacheService;
import com.qifeng.will.canal.service.inter.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redis")
@Slf4j
public class CachedController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("test")
    public String test(String id){
        log.info("here.................");
        return cacheService.test(id);
    }
}
