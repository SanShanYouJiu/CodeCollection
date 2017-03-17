package DesignMode.DesignModel.Visitor.Visitor2;

/**
 * Created by han on 2017/3/9.
 */
public class ConcreteElement2 extends Element  {

    @Override
    public void doSomething() {
        //业务逻辑
    }

    @Override
    public void accept(IVisitor visitor) {
     visitor.visit(this);
    }

}
