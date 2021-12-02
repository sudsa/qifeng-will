package com.qifeng.will.strategy.simple2;

import com.qifeng.will.strategy.simple2.SimpleStrategy;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈策略模式实现2〉
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
//@Service
public class LongSimple implements SimpleStrategy<Long> {

    @Override
    public void startProcess(Long aLong, Boolean againFlag) {
        System.out.println("策略模式实现2-String");
    }

    @Override
    public void handleInstanceAgree(Object instance) {
        System.out.println("策略模式实现2-instance");
    }
}
