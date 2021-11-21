package com.hanxiaozhang.transaction;

import com.hanxiaozhang.common.DictTwoDO;

import java.io.FileNotFoundException;

/**
 * 〈一句话功能简述〉<br>
 * 〈事务服务接口1〉
 *
 * @author hanxinghua
 * @create 2020/4/12
 * @since 1.0.0
 */
public interface TxService1 {



    /**
     * 处理运行时异常的事务方法
     *
     * @param dict
     * @throws FileNotFoundException
     */
    void handleRuntimeExceptionTx(DictTwoDO dict) throws FileNotFoundException;


    /**
     * 处理所有异常的事务方法
     *
     * @param dict
     * @throws FileNotFoundException
     */
    void handleExceptionTx(DictTwoDO dict) throws FileNotFoundException;


    /**
     * 处理捕获异常的事务方法
     *
     * @param dict
     */
    void handleCatchException(DictTwoDO dict);


    /**
     * 处理不捕获异常的事务方法
     *
     * @param dict
     */
    void handleNonCatchException(DictTwoDO dict);


    /**
     * 同一个类中，处理没有事务方法调用事务方法
     *
     * @param dict
     */
    void handleNonTxCellTxOneClass(DictTwoDO dict);

    /**
     * 事务方法1
     *
     * @param dict
     */
    void tx_1(DictTwoDO dict);


    /**
     * 同一个类中，处理事务方法调用事务方法1
     *
     * @param dict
     */
    void handleTxCellTxOneClass_1(DictTwoDO dict);


    /**
     * 事务方法2
     *
     * @param dict
     */
    void tx_2(DictTwoDO dict);

    /**
     * 同一个类中，处理事务方法调用事务方法2
     * 外层传播规则：REQUIRED
     * 内层传播规则：REQUIRED_NEW
     *
     * @param dict
     */
    void handleTxCellTxOneClass_2(DictTwoDO dict);


    /**
     * 使用REQUIRED_NEW传播规则的事务1
     *
     * @param dict
     */
    void propagationRequiredNewTx_1(DictTwoDO dict);


    /**
     * 同一个类中，处理事务方法调用事务方法3（使用CGLIB动态代理）
     *
     * @param dict
     */
    void handleTxCellTxOneClass_3(DictTwoDO dict);


    /**
     * 同两个类中，无事务方法调用另一个类事务方法
     *
     * @param dict
     */
    void handleNonTxCellTxTwoClass(DictTwoDO dict);

    /**
     * 使用REQUIRED传播规则的事务1
     *
     * @param dict
     */
    void propagationRequiredTx_1(DictTwoDO dict);


    /**
     * 使用REQUIRED_NEW传播规则的事务2
     *
     * @param dict
     */
    void propagationRequiredNewTx_2(DictTwoDO dict);


    /**
     * 同两个类中，处理的嵌套事务1
     * 外层传播规则：REQUIRED
     * 内层传播规则：REQUIRED_NEW
     *
     *
     * @param dict
     */
    void handleNestTxOnTwoClass_1(DictTwoDO dict);


    /**
     * 处理同两个类中的嵌套事务2
     * 外层传播规则：REQUIRED
     * 内层传播规则：NESTED
     *
     * @param dict
     */
    void handleNestTxOnTwoClass_2(DictTwoDO dict);





    //--------------------模拟操作----------------------------

    /**
     * 模拟最后一个节点，需要的操作
     */
    void lastNode();


}
