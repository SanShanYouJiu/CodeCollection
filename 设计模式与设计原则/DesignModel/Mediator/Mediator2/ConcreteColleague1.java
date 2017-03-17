package DesignMode.DesignModel.Mediator.Mediator2;

/**
 * Created by han on 2017/2/26.
 */
public class ConcreteColleague1 extends Colleague {
    //通过构造函数传入中介者
    public ConcreteColleague1(Mediator _mediator) {
        super(_mediator);
    }

    //自有方法 selfMethod
    public void selfMethod(){
        //处理自己的业务逻辑
    }

    //依赖方法 depMethod
    public void depMethod1() {
        //处理自己的业务逻辑
      super.mediator.doSomething1();
    }


}
