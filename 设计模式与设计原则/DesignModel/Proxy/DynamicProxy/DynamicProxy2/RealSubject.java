package DesignMode.DesignModel.Proxy.DynamicProxy.DynamicProxy2;

/**
 * Created by han on 2017/2/23.
 */
public class RealSubject implements Subject {

    @Override
    public void doSomething(String str) {
        System.out.println("do something! -->" + str);
    }
}
