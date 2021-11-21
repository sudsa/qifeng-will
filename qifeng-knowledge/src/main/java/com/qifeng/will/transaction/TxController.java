package com.hanxiaozhang.transaction;

import com.hanxiaozhang.common.DictTwoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;

/**
 * 〈一句话功能简述〉<br>
 * 〈事务测试控制器〉
 *
 * @author hanxinghua
 * @create 2020/4/12
 * @since 1.0.0
 */
@Slf4j
@Controller
@RequestMapping("/tx")
public class TxController {



    @Autowired
    private TxService1 txservice1;


    /*
    为什么在同一个类中，一个方法调用另一个方法，Spring代理不生效?
    答：在Spring中，如果目标对象（target）实现了接口，Spring采用基于JDK接口形式的动态代理，代理的原理就是
        对target前后左右做拦截。@transactional注解的service类，这里Service类就是target，我们在Service类
        内部一个方法调用另一个方法时，是target内部的调用（this.method()），并没有走外层的拦截，所有不生效。

        REQUIRES_NEW与NESTED最根本区别：
        REQUIRES_NEW新创建一个事务，自己提交；而NESTED还在一个事务中，它与主事务一块提交。
        (1) NESTED的内层事务可以读取主事务未提交的数据；REQUIRES_NEW却不行，除非内层事务的隔离级别是Read Uncommitted。
        (2) NESTED的内层事务执行结束后，外层事务出现异常，内层事务可以回滚；REQUIRES_NEW却不行，因为REQUIRES_NEW的内层事务已提交。
        (3) NESTED可以修改同一条数据；REQUIRES_NEW会造成等锁超时或死锁。
        (4) REQUIRES_NEW内层事务独自提交后，可以被修改，可能造成A的脏读。



     */


    @RequestMapping("")
    @ResponseBody
    public String test(){
        DictTwoDO dict = new DictTwoDO();
        dict.setName("事务测试").setValue("事务测试").setType("事务测试");

        System.out.println("----------证明1：Spring事务默认只处理RuntimeException异常----------");
//        proof1(dict);

        System.out.println("----------证明2：Spring事务捕获了异常后，事务不会回滚----------");
//        proof2(dict);

        System.out.println("----------证明3：Spring事务在同一类之间方法调用事务是否生效----------");
        System.out.println("----------（使用CGLIB解决代理不生效）---------");
//        proof3(dict);

        System.out.println("----------证明4：Spring事务在两个类中，无事务方法调用另一个类事务方法是否生效----------");
//        proof4(dict);

        System.out.println("----------证明5：REQUIRED与REQUIRED_NEW事务传播规则在只有一个事务方法时，效果一样----------");
        System.out.println("----------（侧面说明：REQUIRED_NEW作为嵌套事务的内层事务有意义）----------");
//        proof5(dict);

        System.out.println("----------说明1：嵌套事务，内层事务为REQUIRED_NEW与NESTED的区别----------");
        explain1(dict);

        System.out.println("----------说明2：嵌套事务，内层事务为REQUIRED与SUPPORTS的区别----------");
        //外层传播规则：REQUIRED，内层传播规则：REQUIRED：
        // 如果外层方法存在事务，内层事务直接加入到外层事务中，即，两个事务在同一个物理事务中。反之，自己开启一个事务
        //外层传播规则：REQUIRED，内层传播规则：SUPPORTS：
        // 如果外层方法存在事务，内层事务直接加入到外层事务中，即，两个事务在同一个物理事务中。反之，自己不开启一个事务



        return "OK";
    }



    /**
     * 说明1：嵌套事务，内层事务为REQUIRED_NEW与NESTED的区别
     *
     * 解释：handleNestTxOnTwoClass_1(DictTwoDO dict):外层传播规则：REQUIRED，内层传播规则：REQUIRED_NEW
     * 内层新开启一个事务。内层事务异常，可能会影响外部事务的回滚，即如果外层事务捕获内层事务的异常，则外层事务不会回滚，
     * 如果外层事务不处理内层事务的异常，则外层事务回滚；但外层事务异常，不会影响内部事务的回滚。
     *
     * 解释：handleNestTxOnTwoClass_2(DictTwoDO dict):外层传播规则：REQUIRED，内层传播规则：NESTED
     * 内层在当前事务上创建保存点（SavePoint）并开启子事务，子事务会与主事务一同提交。内层事务异常，将回滚到它执行前的保存点（SavePoint)，
     * 此时，如果外层事务捕获内层事务的异常，则外层事务不会回滚，如果外层事务不处理内层事务的异常，则外层事务回滚；
     * 但外层事务异常，内部事务也会回滚。
     *
     *
     * @param dict
     */
    private void explain1(DictTwoDO dict) {
//        txservice1.handleNestTxOnTwoClass_1(dict);
        txservice1.handleNestTxOnTwoClass_2(dict);
    }





    /**
     * 证明5：REQUIRED与REQUIRED_NEW事务传播规则在只有一个事务方法时，效果一样
     * 结果：
     * 两个都会回滚
     *
     * @param dict
     */
    private void proof5(DictTwoDO dict) {
        txservice1.propagationRequiredTx_1(dict);
//        txservice1.propagationRequiredNewTx_2(dict);
    }


    /**
     * 证明4：Spring事务在两个类中，无事务方法调用另一个类事务方法是否生效
     * 结果：只是事务方法生效
     *
     * @param dict
     */
    private void proof4(DictTwoDO dict) {
        txservice1.handleNonTxCellTxTwoClass(dict);
    }

    /**
     * 证明3：Spring事务在同一类之间方法调用事务是否生效
     * 结果：
     * handleNonTxCellTxOneClass(DictTwoDO dict)
     * 在同一类中，普通方法调用事务方法，不会开启事务，因为spring采用动态代理机制来实现事务控制，同类调用不会产生代理。
     * handleTxCellTxOneClass_1(DictTwoDO dict)
     * handleTxCellTxOneClass_2(DictTwoDO dict)
     * 在同一类中，事务方法调用事务方法，内层方法事务不会生效，作为普通方法加入外层事务，
     * 因为spring采用动态代理机制来实现事务控制，同类调用不会产生代理。
     *
     * 如何让注解生效，使用CGLIB动态代理：
     * https://blog.csdn.net/rainbow702/article/details/53907474
     * handleTxCellTxOneClass_3
     *
     * @param dict
     */
    private void proof3(DictTwoDO dict) {
//        txservice1.handleNonTxCellTxOneClass(dict);
//        txservice1.handleTxCellTxOneClass_1(dict);
//        txservice1.handleTxCellTxOneClass_2(dict);
        txservice1.handleTxCellTxOneClass_3(dict);

    }

    /**
     * 证明2：Spring事务捕获了异常后，事务不会回滚
     * 结果：
     * handleCatchException(DictTwoDO dict)捕获了异常后，事务不会回滚
     * handleNonCatchException(DictTwoDO dict)不捕获了异常后，事务回滚
     *
     * @param dict
     */
    private void proof2(DictTwoDO dict) {
//        txservice1.handleCatchException(dict);
        txservice1.handleNonCatchException(dict);
    }

    /**
     * 证明1：Spring事务默认只处理RuntimeException异常
     * 结果：
     * handleRuntimeExceptionTx(DictTwoDO dict)遇到非运行异常时，事务不回滚
     * handleExceptionTx(DictTwoDO dict)遇到非运行异常时，事务回滚
     *
     * @param dict
     */
    private void proof1(DictTwoDO dict) {
        try {
            //txservice1.handleRuntimeExceptionTx(dict);
            txservice1.handleExceptionTx(dict);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




    @RequestMapping("/test1")
    @ResponseBody
    public String test1(){
        txservice1.lastNode();
        return "OK";
    }



}
