package DesignMode.DesignModel.Proxy.DynamicProxy.DynamicProxy2;

/**
 * Created by han on 2017/2/23.
 */
public class Client {

    public static void main(String[] args) {
//        Subject subject = new RealSubject();
//        InvocationHandler handler = new MyInvocationHandler(subject);
//        Subject proxy = DynamicProxy.newProxyInstance(subject.getClass().getClassLoader(),
//                subject.getClass().getInterfaces(), handler);
//        proxy.doSomething("Finish");

        Subject subject = new RealSubject();
        Subject proxy=SubjectDynamicProxy.newProxyInstance(subject);
        proxy.doSomething("测试");

    }
}
