package com.hanxiaozhang.transaction;

import com.hanxiaozhang.common.DictTwoDO;

/**
 * 〈一句话功能简述〉<br>
 * 〈事务服务接口2〉
 *
 * @author hanxinghua
 * @create 2020/4/12
 * @since 1.0.0
 */
public interface TxService2 {

    /**
     * 事务方法1
     *
     * @param dict
     */
    void tx_1(DictTwoDO dict);

    /**
     * 使用REQUIRED_NEW传播规则的事务1
     *
     * @param dict
     */
    void propagationRequiredNewTx_1(DictTwoDO dict);


    /**
     * 使用NESTED传播规则的事务1
     *
     * @param dict
     */
    void propagationNestedTx_1(DictTwoDO dict);



    //--------------------模拟操作----------------------------


    /**
     * 模拟系统还款操作
     */
    void systemRepayment();

}
