package com.qifeng.will.strategy.simple3;

import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
@Service
public class LongSimple extends AbstractStrategyAdapter<Long> {

    @Override
    public void startProcessOperate(Long aLong) {
        System.out.println("startProcessOperate-LongSimple-Long");
    }

    @Override
    public void instanceAgreeOperate(Object instance) {
        super.test();
        System.out.println("startProcessOperate-LongSimple");
    }
}
