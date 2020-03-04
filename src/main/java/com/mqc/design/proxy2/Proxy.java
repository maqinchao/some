package com.mqc.design.proxy2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author Administrator
 * @create 2020/2/29 9:16
 */
public class Proxy implements InvocationHandler {
    private Object object;

    public Proxy() {
    }

    public Object createInstance(Object pursue) {
        this.object=pursue;
        return java.lang.reflect.Proxy.newProxyInstance(this.object.getClass().getClassLoader(),this.object.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在真实的对象执行之前我们可以添加自己的操作
        System.out.println("before invoke。。。");
        Object invoke = method.invoke(this.object, args);
        //在真实的对象执行之后我们可以添加自己的操作
        System.out.println("after invoke。。。");
        return invoke;
    }

    public static void main(String[] args) {
        Proxy proxy=new Proxy();
        Pursue pursue= (Pursue) proxy.createInstance(new Suitors());
//        Pursue pursue=new Suitors();
        pursue.sendGift("");

    }

}
