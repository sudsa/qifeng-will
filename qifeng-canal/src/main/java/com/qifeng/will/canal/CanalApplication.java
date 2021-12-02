package com.qifeng.will.canal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
//@EnableAsync
@EnableScheduling
@EnableCaching
public class CanalApplication {

    public static void main(String[] args) {

        SpringApplication.run(CanalApplication.class, args);
        System.out.println("server start finished");
    }

}
