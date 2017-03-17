package DesignMode.DesignModel.Visitor.Visitor2;

/**
 * Created by han on 2017/3/9.
 */
public interface IVisitor {
    //可访问哪些对象
    public void visit(ConcreteElement1 element1);
    //可以访问哪些对象
    public void  visit(ConcreteElement2 element2);
}
