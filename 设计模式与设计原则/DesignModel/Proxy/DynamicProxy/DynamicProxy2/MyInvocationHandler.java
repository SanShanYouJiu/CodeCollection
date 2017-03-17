package DesignMode.DesignModel.Proxy.DynamicProxy.DynamicProxy2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by han on 2017/2/23.
 */
public class MyInvocationHandler implements InvocationHandler {

    //被代理的对象
    private Object target = null;

    //通过构造函数传递一个对象
    public MyInvocationHandler(Object obj) {
        this.target = obj;
    }

    //代理方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(this.target, args);
    }
}
