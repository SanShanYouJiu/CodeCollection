package DesignMode.DesignModel.Strategy.Strategy2;

/**
 * Created by han on 2017/3/1.
 */
public class Context {
    //抽象策略
    private Strategy strategy = null;

    //构造函数设置具体策略
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    //封装后的策略方法
    public void doAnything(){
        this.strategy.doSomething();
    }
}
