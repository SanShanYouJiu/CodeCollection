package DesignMode.DesignModel.Mediator.Mediator2;

/**
 * Created by han on 2017/2/26.
 */
public class ConcreteMediator extends Mediator {

    @Override
    public void doSomething1() {
        //调用同事类的方法  只要是public方法都能可以调用
        super.c1.depMethod1();
        super.c2.depMethod1();
    }


    @Override
    public void doSomething2() {
          super.c1.selfMethod();
          super.c2.selfMethod();
    }


}
