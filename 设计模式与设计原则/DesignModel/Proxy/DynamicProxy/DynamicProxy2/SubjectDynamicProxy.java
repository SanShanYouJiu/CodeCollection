package DesignMode.DesignModel.Proxy.DynamicProxy.DynamicProxy2;

import java.lang.reflect.InvocationHandler;

/**
 * Created by han on 2017/2/23.
 */
public class SubjectDynamicProxy extends DynamicProxy {


    public static <T> T newProxyInstance(Subject subject) {
        //获得ClassLoader
        ClassLoader loader = subject.getClass().getClassLoader();
        //获得接口人气
        Class<?>[] classes = subject.getClass().getInterfaces();
        //获得Handler
        InvocationHandler handler = new MyInvocationHandler(subject);
        return newProxyInstance(loader, classes, handler);
    }
}
