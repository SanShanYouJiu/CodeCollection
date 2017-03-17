package DesignMode.DesignModel.Strategy.Strategy2;

/**
 * Created by han on 2017/3/1.
 */
public class ConcreteStrategy1 implements Strategy {

    @Override
    public void doSomething() {
        System.out.println("具体策略一运算法则");
    }
}
