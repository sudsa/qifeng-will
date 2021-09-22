package com.hanxiaozhang.strategy.simple2;

import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈策略模式实现1〉
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
//@Service
public class StringSimple implements SimpleStrategy<String> {

    @Override
    public void startProcess(String s, Boolean againFlag) {
        System.out.println("策略模式实现1-String");
    }

    @Override
    public void handleInstanceAgree(Object instance) {
        System.out.println("策略模式实现1-instance");
    }

}
