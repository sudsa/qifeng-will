package com.hanxiaozhang.beanlifecycle;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/3/16
 * @since 1.0.0
 */
public class Test {

    public static void main(String[] args) {

        //BeanFactory 初始化IOC容器
        Resource resource = new ClassPathResource("config/spring-bean.xml");
        BeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinitionReader bdr = new XmlBeanDefinitionReader((BeanDefinitionRegistry) factory);
        bdr.loadBeanDefinitions(resource);
        Bean bean = (Bean)factory.getBean("bean");
        System.out.println(bean.getName());

        //ApplicationContext 初始化IOC容器
//        System.out.println("开始启动容器");
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring-bean.xml");
//        Bean bean = applicationContext.getBean("bean", Bean.class);
//        System.out.println(bean.getName());
//
//        System.out.println("现在开始关闭容器！");
//        ((ClassPathXmlApplicationContext)applicationContext).registerShutdownHook();


    }
}
