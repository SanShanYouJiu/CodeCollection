package DesignMode.DesignModel.Facade.Facade2;

/**
 * Created by han on 2017/3/7.
 */
public class Context {
    private ClassA a = new ClassA();
    private ClassB b = new ClassB();
    public void complexMethod(){
        this.a.doSomethingA();
        this.b.doSomethingB();
    }
}
