package DesignMode.DesignModel.Mediator.Mediator2;

/**
 * Created by han on 2017/2/26.
 */
public abstract class Mediator {
    //定义同事类
    protected ConcreteColleague1 c1;
    protected  ConcreteColleague2 c2;

    //通过setter/getter方法把同事类注入进来
    public ConcreteColleague1 getC1() {
        return c1;
    }

    public void setC1(ConcreteColleague1 c1) {
        this.c1 = c1;
    }

    public ConcreteColleague2 getC2() {
        return c2;
    }

    public void setC2(ConcreteColleague2 c2) {
        this.c2 = c2;
    }


    //中介者自己的业务逻辑
    public abstract  void doSomething1();

    public abstract  void doSomething2();
}
