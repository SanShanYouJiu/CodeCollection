package DesignMode.DesignModel.Mediator.Mediator2;

/**
 * Created by han on 2017/2/26.
 */
public class ConcreteColleague2 extends Colleague {
        //通过构造函数传递给中介者
    public ConcreteColleague2(Mediator _mediator) {
        super(_mediator);
    }

    //自有方法 通过构造函数传递给中介者
    public void selfMethod(){
        //处理自己的业务逻辑
    }

     //依赖方法 depMethod
    public void depMethod1() {
        //处理自己的业务逻辑
        //自己不能处理的业务逻辑 委托给中介者处理
        super.mediator.doSomething1();
    }
}
