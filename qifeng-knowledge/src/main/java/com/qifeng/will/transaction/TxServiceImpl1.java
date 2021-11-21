package com.hanxiaozhang.transaction;

import com.hanxiaozhang.common.DictTwoDO;
import com.hanxiaozhang.common.DictTwoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/4/12
 * @since 1.0.0
 */
@Slf4j
@Service
//开启GCLIB动态代理
@EnableAspectJAutoProxy(exposeProxy = true)
public class TxServiceImpl1 implements TxService1 {

    @Resource
    private DictTwoDao dictTwoDao;

    @Autowired
    private TxService2 txService2;


    @Override
    @Transactional
    public void handleRuntimeExceptionTx(DictTwoDO dict) throws FileNotFoundException {
        dict.setDescription("TxService1:handleRuntimeExceptionTx");
        dictTwoDao.save(dict);
        // 这里会抛一个IO流异常：java.io.FileNotFoundException: E:\a.txt (系统找不到指定的路径。)
        FileInputStream fileInputStream = new FileInputStream("E:\\a.txt");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleExceptionTx(DictTwoDO dict) throws FileNotFoundException {
        dict.setDescription("TxService1:handleExceptionTx");
        dictTwoDao.save(dict);
        // 这里会抛一个IO流异常：java.io.FileNotFoundException: E:\a.txt (系统找不到指定的路径。)
        FileInputStream fileInputStream = new FileInputStream("E:\\a.txt");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleCatchException(DictTwoDO dict) {
        dict.setDescription("TxService1:handleCatchException");
        dictTwoDao.save(dict);
        try {
            throw  new RuntimeException("抛一个异常");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleNonCatchException(DictTwoDO dict) {
        dict.setDescription("TxService1:handleNonCatchException");
        dictTwoDao.save(dict);
        throw  new RuntimeException("抛一个异常");
    }

    @Override
    public void handleNonTxCellTxOneClass(DictTwoDO dict) {
        dict.setDescription("TxService1:handleNonTxCellTxOneClass");
        dictTwoDao.save(dict);
//        this.tx_1(dict);
        ((TxServiceImpl1)AopContext.currentProxy()).tx_1(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void tx_1(DictTwoDO dict) {
        dict.setDescription("TxService1:tx_1");
        dictTwoDao.save(dict);
        throw  new RuntimeException("抛一个异常");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleTxCellTxOneClass_1(DictTwoDO dict) {
        dict.setDescription("TxService1:handleTxCellTxOneClass_1");
        dictTwoDao.save(dict);
        this.tx_2(dict);
        throw  new RuntimeException("抛一个异常");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void tx_2(DictTwoDO dict) {
        dict.setDescription("TxService1:tx_2");
        dictTwoDao.save(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleTxCellTxOneClass_2(DictTwoDO dict) {
        dict.setDescription("TxService1:handleTxCellTxOneClass_2");
        dictTwoDao.save(dict);
        this.propagationRequiredNewTx_1(dict);
        throw  new RuntimeException("抛一个异常");
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void propagationRequiredNewTx_1(DictTwoDO dict) {
        dict.setDescription("TxService1:propagationRequiredNewTx_1:Propagation.REQUIRES_NEW");
        dictTwoDao.save(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleTxCellTxOneClass_3(DictTwoDO dict) {
        dict.setDescription("TxService1:handleTxCellTxOneClass_3:Propagation.REQUIRED");
        dictTwoDao.save(dict);
        ((TxServiceImpl1)AopContext.currentProxy()).propagationRequiredNewTx_1(dict);
        throw  new RuntimeException("抛一个异常");
    }


    @Override
    public void handleNonTxCellTxTwoClass(DictTwoDO dict) {
        dict.setDescription("TxService1:handleNonTxCellTxTwoClass");
        dictTwoDao.save(dict);
        txService2.tx_1(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void propagationRequiredTx_1(DictTwoDO dict) {
        dict.setDescription("TxService1:propagationRequiredTx_1");
        dictTwoDao.save(dict);
        throw  new RuntimeException("抛一个异常");
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void propagationRequiredNewTx_2(DictTwoDO dict) {
        dict.setDescription("TxService1:propagationRequiredNewTx_2");
        dictTwoDao.save(dict);
        throw  new RuntimeException("抛一个异常");
    }


    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void handleNestTxOnTwoClass_1(DictTwoDO dict) {
        dict.setDescription("TxService1:handleNestTxOnTwoClass_1:Propagation.REQUIRED");
        dictTwoDao.save(dict);
        // 声明一个异常  // 第1处
//        int i=1;
//        i=i/0;
        try {
            txService2.propagationRequiredNewTx_1(dict);
        } catch (Exception e) {
            dict.setDescription("TxService1:handleNestTxOnTwoClass_1:fair");
            dictTwoDao.save(dict);
            // 声明一个异常  // 第3处
//        throw  new RuntimeException("抛一个异常");
        }
        dict.setDescription("TxService1:handleNestTxOnTwoClass_1:two");
        dictTwoDao.save(dict);
        // 声明一个异常  // 第4处
        throw  new RuntimeException("抛一个异常");

    }


    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void handleNestTxOnTwoClass_2(DictTwoDO dict) {

        dict.setDescription("TxService1:handleNestTxOnTwoClass_2:Propagation.REQUIRED");
        dictTwoDao.save(dict);
        // 声明一个异常  // 第1处
//        int i=1;
//        i=i/0;
        try {
            txService2.propagationNestedTx_1(dict);
        } catch (Exception e) {
            dict.setDescription("TxService1:handleNestTxOnTwoClass_2:fair");
            dictTwoDao.save(dict);
            // 声明一个异常  // 第3处
//        throw  new RuntimeException("抛一个异常");
        }
        dict.setDescription("TxService1:handleNestTxOnTwoClass_2:two");
        dictTwoDao.save(dict);
        // 声明一个异常  // 第4处
        throw  new RuntimeException("抛一个异常");
    }




 //--------------------模拟操作----------------------------


    /**
     * 模拟最后一个节点，需要的操作
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lastNode() {

        try {
            //模拟更新申请单状态
            dictTwoDao.updateStatusById(2L,"审批通过");
            //系统还款
            txService2.systemRepayment();
            //保存成功审批记录
            System.out.println("模拟成功审批记录");

        } catch (Exception e) {
            String errorMsg=e.getMessage();
            //保存失败审批记录
            System.out.println("模拟失败审批记录"+errorMsg);
        }

    }


}
