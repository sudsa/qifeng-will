package com.hanxiaozhang.designpattern.strategy.service.impl;

import com.hanxiaozhang.designpattern.strategy.domain.A;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *  建议强制指定一下bean的id
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
@Service("aProcess")
public class AProcess extends AbstractProcessStrategyAdapter<A> {

    @Override
    public Object startProcessOperateBefore(A a, Boolean againFlag) {

        System.out.println("流程A发起流程前的准备...");

        return new Object();
    }

    @Override
    public void startProcessOperateAfter(A a, Boolean againFlag) {

        System.out.println("流程A发起流程成功后的处理...");

    }

    @Override
    public void instanceAgreeOperate(Object instance) {

        System.out.println("流程A同意的处理...");

    }


}
