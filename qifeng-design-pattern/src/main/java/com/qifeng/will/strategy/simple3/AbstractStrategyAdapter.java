package com.qifeng.will.strategy.simple3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象策略的缺醒适配器〉
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
public  abstract  class AbstractStrategyAdapter<T> implements SimpleStrategy<T> {

    @Autowired
    private  Bean bean;

    @Override
    public  void  startProcess(T t, Boolean againFlag){
        this.startProcessOperate(t);
    }

    /**
     * 发起流程的操作
     *
     * @param t
     */
    public abstract void startProcessOperate(T t);


    @Override
    @Transactional(rollbackFor = Exception.class)
    public  void  handleInstanceAgree(Object instance) {
        this.saveAgreeApprovalLog();
        this.instanceAgreeOperate(instance);
    }

    /**
     * 实例同意的操作
     *
     * @param instance
     */
    public abstract void instanceAgreeOperate(Object instance);


    /**
     * 保存实例同意的审批记录
     */
    private void saveAgreeApprovalLog(){
        System.out.println(bean.mgs);
    }


    /**
     * 测试super的使用
     */
    public void test(){
        System.out.println("call super class method");
    }

}
