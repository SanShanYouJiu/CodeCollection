package DesignMode.DesignModel.Bridge.Bridge2;

/**
 * Created by han on 2017/3/14.
 */
public class Client {
    public static void main(String[] args) {
        //实现一个实现化角色
        Implementor imp = new ConcreteImplementor1();
        //实现一个抽象化角色
        Abstraction abs = new RefinedAvstraction(imp);
        //执行行位
        abs.request();
    }
}
