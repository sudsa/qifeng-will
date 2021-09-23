package com.qifeng.will.userserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
//@ComponentScan(basePackages="com.qifeng.will.userserver.service")
@EnableSwagger2
@EnableAsync
//@EnableFeignClients(basePackages = "com.qifeng.will.userserver.feign")
@Slf4j
public class UserServerApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(UserServerApplication.class, args);
        }finally {
            log.info("server start finish");
        }
    }
}
