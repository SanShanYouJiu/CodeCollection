package DesignMode.DesignModel.Proxy.DynamicProxy.DynamicProxy2;

/**
 * Created by han on 2017/2/23.
 */
public class BeforeAdvice implements IAdivce {
    @Override
    public void exec() {
        System.out.println("我是前置通知 我被执行了");
    }
}
