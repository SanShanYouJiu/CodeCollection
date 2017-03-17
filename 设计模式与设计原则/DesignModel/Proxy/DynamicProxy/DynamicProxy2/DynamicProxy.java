package DesignMode.DesignModel.Proxy.DynamicProxy.DynamicProxy2;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by han on 2017/2/23.
 */
public class DynamicProxy<T> {


    public static <T> T newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h){
        if (true){
            (new BeforeAdvice()).exec();
        }
        return (T)Proxy.newProxyInstance(loader, interfaces, h);
    }


}