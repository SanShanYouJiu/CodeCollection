package DesignMode.DesignModel.Visitor.Visitor2;

/**
 * 抽象元素
 * Created by han on 2017/3/9.
 */
public abstract class Element {
    //定义业务逻辑
    public abstract void doSomething();
    //允许谁来访问
    public abstract void accept(IVisitor visitor);
}
