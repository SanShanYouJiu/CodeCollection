package DesignMode.DesignModel.Bridge.Bridge2;

/**
 * Created by han on 2017/3/14.
 */
public class Abstraction {
    //实现对实现化角色的引用
    private Implementor imp;

    //约束子类必须实现该构造函数
    public Abstraction(Implementor imp) {
        this.imp = imp;
    }

    //自身的行为和属性
    public void request() {
        this.imp.doSomething();
    }

    //获得实现化角色
    public Implementor getImp() {
        return imp;
    }
}
