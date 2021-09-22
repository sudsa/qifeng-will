package com.hanxiaozhang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.hanxiaozhang.*.dao")
@ServletComponentScan
@SpringBootApplication
public class SpringbootRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisApplication.class, args);
        System.out.println("   ヾ(◍°∇°◍)ﾉﾞ        START UP SUCCESS       ヾ(◍°∇°◍)ﾉﾞ   \n" +
                "        __________________      _     _            \n" +
                "       /  |    _     _    |    | |   | | _____    __    _       \n" +
                "  ____/___|   |_|   |_|   |    | |___| |/ ___  \\ |  \\  | |    \n" +
                " /   | |  |_______________|    |  ___  | |   | | | |\\\\ | |     \n" +
                "/____|_|__________________|    | |   | | |___|  \\| | \\\\| |    \n" +
                "    |_._|       |_._|          |_|   |_|______/\\_\\_|  \\__|   ");
    }

}
