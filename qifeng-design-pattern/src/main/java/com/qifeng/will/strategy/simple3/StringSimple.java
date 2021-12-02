package com.qifeng.will.strategy.simple3;

import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author howill.zou
 * @create 2020/5/11
 * @since 1.0.0
 */
@Service
public class StringSimple extends AbstractStrategyAdapter<String> {

    @Override
    public void startProcessOperate(String o) {
        System.out.println("startProcessOperate-StringSimple-String");
    }

    @Override
    public void instanceAgreeOperate(Object instance) {
        System.out.println("startProcessOperate-instanceAgreeOperate");
    }
}
