package com.hanxiaozhang.transaction;

import com.hanxiaozhang.common.DictTwoDO;
import com.hanxiaozhang.common.DictTwoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
public class TxServiceImpl2 implements TxService2{

    @Resource
    private DictTwoDao dictTwoDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void tx_1(DictTwoDO dict) {
        dict.setDescription("TxService2:tx_1");
        dictTwoDao.save(dict);
        throw  new RuntimeException("抛一个异常");
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void propagationRequiredNewTx_1(DictTwoDO dict) {
        dict.setDescription("TxService2:propagationRequiredNewTx_1:Propagation.REQUIRES_NEW");
        dictTwoDao.save(dict);
        // 声明一个异常  // 第2处
//        throw  new RuntimeException("抛一个异常");
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    public void propagationNestedTx_1(DictTwoDO dict) {
        dict.setDescription("TxService2:propagationNestedTx_1:Propagation.NESTED");
        dictTwoDao.save(dict);
        // 声明一个异常  // 第2处
//        throw  new RuntimeException("抛一个异常");
    }


    //--------------------模拟操作----------------------------


    /**
     * 模拟系统还款操作
     */
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    public void systemRepayment() {

        //系统还款
        System.out.println("模拟系统还款");

        //更新申请单状态
        dictTwoDao.updateStatusById(2L,"已还款");

//        //模拟系统还款异常
//        throw  new RuntimeException("系统还款异常");

        //更新其他状态
        System.out.println("模拟更新其他状态");

    }
}
