package com.qifeng.will;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
public class WebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxApplication.class, args);
        System.out.println("   ヾ(◍°∇°◍)ﾉﾞ        START UP SUCCESS       ヾ(◍°∇°◍)ﾉﾞ   \n" +
                "        __________________      _     _            \n" +
                "       /  |    _     _    |    | |   | | _____    __    _       \n" +
                "  ____/___|   |_|   |_|   |    | |___| |/ ___  \\ |  \\  | |    \n" +
                " /   | |  |_______________|    |  ___  | |   | | | |\\\\ | |     \n" +
                "/____|_|__________________|    | |   | | |___|  \\| | \\\\| |    \n" +
                "    |_._|       |_._|          |_|   |_|______/\\_\\_|  \\__|   ");
    }

}
