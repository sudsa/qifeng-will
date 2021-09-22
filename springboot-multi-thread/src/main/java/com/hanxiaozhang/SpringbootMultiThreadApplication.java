package com.hanxiaozhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class SpringbootMultiThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMultiThreadApplication.class, args);
        System.out.println("   ヾ(◍°∇°◍)ﾉﾞ        START UP SUCCESS       ヾ(◍°∇°◍)ﾉﾞ   \n" +
                "        __________________      _     _            \n" +
                "       /  |    _     _    |    | |   | | _____    __    _       \n" +
                "  ____/___|   |_|   |_|   |    | |___| |/ ___  \\ |  \\  | |    \n" +
                " /   | |  |_______________|    |  ___  | |   | | | |\\\\ | |     \n" +
                "/____|_|__________________|    | |   | | |___|  \\| | \\\\| |    \n" +
                "    |_._|       |_._|          |_|   |_|______/\\_\\_|  \\__|   ");
    }

}
