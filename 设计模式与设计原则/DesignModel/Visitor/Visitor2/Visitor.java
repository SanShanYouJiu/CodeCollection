package DesignMode.DesignModel.Visitor.Visitor2;

/**
 * Created by han on 2017/3/9.
 */
public class Visitor  implements IVisitor{
    public void visit(ConcreteElement1 element1) {
        element1.doSomething();
    }

    public void visit(ConcreteElement2 element2) {
          element2.doSomething();
    }

}
