package com.qifeng.will.canal.service;

import com.qifeng.will.canal.service.inter.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisCacheService implements CacheService {

    //@Cacheable(value = "test:",key = "#root.methodName+''+#id+''",cacheManager = "cacheManager")
    @Cacheable(cacheNames="test",keyGenerator = "myKeyGenerator")
    public String test(String id){
        log.info("meizou redis");
        return id;
    }
}
