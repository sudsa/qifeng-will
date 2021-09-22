package com.hanxiaozhang.designpattern.strategy.service.impl;

import com.hanxiaozhang.common.dao.DictDao;
import com.hanxiaozhang.common.domain.DictDO;
import com.hanxiaozhang.designpattern.strategy.service.ProcessStrategy;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象策略的缺省适配器〉
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
public abstract class AbstractProcessStrategyAdapter<T> implements ProcessStrategy<T> {

    @Resource
    private DictDao dictDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public  void  startProcess(T t,Boolean againFlag){

        // 发起流程之前的操作
        Object obj = this.startProcessOperateBefore(t,againFlag);

        // 断言
        assert obj == null:"[startProcessOperateBefore]方法返回值空,调用流程会报错!";

        // 调用流程中心
        System.out.println("模拟调用流程中心..."+obj.toString());

        // 发起流程之后的操作
        this.startProcessOperateAfter(t,againFlag);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public  void  handleInstanceAgree(Object instance) {

        // 模拟保存
        this.saveAgreeApprovalLog(instance);

        // 实例同意的操作
        this.instanceAgreeOperate(instance);

    }


    /**
     * 发起流程之前的操作
     *
     * @param t
     * @param againFlag
     * @return
     */
    public abstract Object startProcessOperateBefore(T t,Boolean againFlag);


    /**
     * 发起流程成功之后的操作
     *
     * @param t
     * @param againFlag
     */
    public abstract void startProcessOperateAfter(T t,Boolean againFlag);


    /**
     * 实例同意的操作
     *
     * @param instance
     */
    public abstract void instanceAgreeOperate(Object instance);


    /**
     * 保存实例同意的审批记录
     *
     * @param instance
     */
    private void saveAgreeApprovalLog(Object instance){

        DictDO dict = DictDO.builder().name("测试一下").build();
        dictDao.save(dict);

//        throw  new RuntimeException("模拟一下异常");
    }


}
