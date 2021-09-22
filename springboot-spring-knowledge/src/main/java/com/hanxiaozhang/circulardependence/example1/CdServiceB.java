package com.hanxiaozhang.circulardependence.example1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈模拟循环依赖ServiceB〉
 *
 * @author hanxinghua
 * @create 2020/5/21
 * @since 1.0.0
 */
@Component
public class CdServiceB {

    /**
     * 1.Setter注入不会出现循环依赖
     */
//    private CdServiceA serviceA;
//
//    public CdServiceA getServiceA() {
//        return serviceA;
//    }
//
//    @Autowired
//    public void setServiceA(CdServiceA serviceA) {
//        this.serviceA = serviceA;
//    }



    /**
     * 2.自动注入不会出现循环依赖
     */
//    @Autowired
//    private CdServiceA serviceA;


    /**
     * 3.构造函数注入会出现循环依赖
     */
//    private CdServiceA serviceA;
//
//    @Autowired
//    public CdServiceB(CdServiceA serviceA){
//        this.serviceA = serviceA;
//    }

    //------------------解决构造函数出现循环依赖问题---------------------
    //1.重新设计，消除循环依赖。
    //2.使用Setter注入
    //3.使用注解 @Lazy
    //一种最简单的消除循环依赖的方式是通过延迟加载。在注入依赖时，先注入代理对象，当首次使用时再创建对象完成注入。
//    private CdServiceA serviceA;
//
//    @Autowired
//    public CdServiceB(@Lazy CdServiceA serviceA){
//        this.serviceA = serviceA;
//    }

    //4.使用@PostConstruct
    private CdServiceA serviceA;

    public CdServiceA getServiceA() {
        return serviceA;
    }

    @Autowired
    public void setServiceA(CdServiceA serviceA) {
        this.serviceA = serviceA;
    }

}
