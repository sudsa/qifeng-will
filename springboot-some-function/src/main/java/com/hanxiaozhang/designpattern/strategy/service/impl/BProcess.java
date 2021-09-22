package com.hanxiaozhang.designpattern.strategy.service.impl;

import com.hanxiaozhang.designpattern.strategy.domain.B;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
@Service("bProcess")
public class BProcess extends AbstractProcessStrategyAdapter<B> {


    @Override
    public Object startProcessOperateBefore(B b, Boolean againFlag) {

        System.out.println("流程B发起流程前的准备...");

        return new Object();
    }

    @Override
    public void startProcessOperateAfter(B b, Boolean againFlag) {

        System.out.println("流程B发起流程成功后的处理...");

    }

    @Override
    public void instanceAgreeOperate(Object instance) {

        System.out.println("流程B同意的处理...");

    }
}
