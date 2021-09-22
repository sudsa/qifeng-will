package com.qifeng.will.dictcode;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/*
 * 功能描述 CGLibProxy动态代理类的实例
 * CGLib采用了非常底层的字节码技术，
 * 其原理是通过字节码技术为一个类创建子类，并在子类中采用方法拦截的技术拦截所有父类方法的调用，
 * 顺势织入横切逻辑。
 * （利用ASM开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理）
 *
 * @author zouhw02
 * @date 2021/9/16
 * @param
 * @return
 */
public class CGLibProxy implements MethodInterceptor {

    private Object targetObject;// CGLib需要代理的目标对象

    public Object createProxyObject(Object obj) {
        this.targetObject = obj;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        Object proxyObj = enhancer.create();
        return proxyObj;// 返回代理对象
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects,
                            MethodProxy methodProxy) throws Throwable {
        Object obj = null;
        if ("addUser".equals(method.getName())) {// 过滤方法
            checkPopedom();// 检查权限
        }
        obj = method.invoke(targetObject, objects);
        return obj;
    }

    private void checkPopedom() {
        System.out.println(".:检查权限 checkPopedom()!");
    }

}
