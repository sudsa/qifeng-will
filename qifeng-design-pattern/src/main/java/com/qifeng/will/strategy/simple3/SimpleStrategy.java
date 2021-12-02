package com.qifeng.will.strategy.simple3;

/**
 * 〈一句话功能简述〉<br>
 * 〈策略模式接口〉
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
public interface SimpleStrategy<T> {


    /**
     * 发起流程
     *
     * @param t
     * @param againFlag 重新发起标识：true 重新发起，false 第一次发起
     */
    void startProcess(T t, Boolean againFlag);

    /**
     * 处理实例同意
     *
     * @param instance
     */
    void handleInstanceAgree(Object instance);



}
