package com.qifeng.will.qifengwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QifengWebfluxApplication {

    public static void main(String[] args) {

        SpringApplication.run(QifengWebfluxApplication.class, args);
        System.out.println("server start finished");
    }

}
