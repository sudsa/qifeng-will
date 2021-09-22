package com.qifeng.will.dictcode;

import cn.hutool.core.date.StopWatch;
import com.qifeng.will.inter.UserManager;
import com.qifeng.will.inter.UserManagerImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/*
 *功能描述 JDK动态代理类
 * 继承了Proxy类，实现了代理的接口
 * 不支持对实现类的代理，只支持接口的代理
 * java的动态代理机制中，有两个重要的类或接口，一个是 InvocationHandler(Interface)、另一个则是 Proxy(Class)
 * InvocationHandler这个接口的唯一一个方法 invoke 方法：
 * @author zouhw02
 * @date 2021/9/16
 * @param
 * @return
 */
public class JDKProxy implements InvocationHandler {

    private Object targetObject;//需要代理的目标对象

    public Object newProxy(Object targetObject) {//将目标对象传入进行代理 
        this.targetObject = targetObject;
        //newProxyInstance(ClassLoader loader, Class<?>[] interfaces,  InvocationHandler handler)  throws IllegalArgumentException
        //loader:　　ClassLoader对象，定义了由哪个ClassLoader来对生成的代理对象进行加载。
        //interfaces:　　Interface对象的数组，表示的是我将要给我需要代理的对象提供一组什么接口，如果我提供了一组接口给它，那么这个代理对象就宣称实现了该接口(多态)，这样我就能调用这组接口中的方法了。
        //Handler：InvocationHandler对象，表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个InvocationHandler对象上。
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(), this);//返回代理对象 
    }

    /*
     *功能描述
     *  proxy: 指代JDK动态生成的最终代理对象
        method: 指代的是我们所要调用真实对象的某个方法的Method对象
        args: 指代的是调用真实对象某个方法时接受的参数
     * @author zouhw02
     * @date 2021/9/16
     * @param
     * @return
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        checkPopedom();//一般我们进行逻辑处理的函数比如这个地方是模拟检查权限
        Object ret = null;   // 设置方法的返回值
        ret = method.invoke(targetObject, args);    //调用invoke方法，ret存储该方法的返回值
        return ret;
    }

    private void checkPopedom() {
        System.out.println(".:检查权限 checkPopedom()!");
    }

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch("耗时工具");
        stopWatch.start("v1");
        TimeUnit.SECONDS.sleep(1);
        stopWatch.stop();
        stopWatch.start("v2");
        UserManager userManager = (UserManager) new CGLibProxy()
                .createProxyObject(new UserManagerImpl());
        System.out.println("-----------CGLibProxy-------------");
        userManager.addUser("tom", "root");
        TimeUnit.SECONDS.sleep(3);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
        System.out.println(stopWatch.prettyPrint());
        StopWatch stopWatch2 = new StopWatch("耗时工具2");
        stopWatch2.start("v3");
        System.out.println("-----------JDKProxy-------------");
        JDKProxy jdkPrpxy = new JDKProxy();
        UserManager userManagerJDK = (UserManager) jdkPrpxy
                .newProxy(new UserManagerImpl());
        userManagerJDK.addUser("tom", "root");
        stopWatch2.stop();
        System.out.println(stopWatch2.getTotalTimeMillis());
        System.out.println(stopWatch2.prettyPrint());
    }
}
