package DesignMode.DesignModel.Facade.Facade2;

/**
 * Created by han on 2017/3/7.
 */
public class Facade {
    //被委托的对象
    private ClassA a = new ClassA();
    private ClassB b = new ClassB();
    private Context context = new Context();

    public void methodA(){
        this.a.doSomethingA();
    }

    public void methodB(){
        this.b.doSomethingB();
    }
    public void methodC(){
        this.context.complexMethod();
    }
}
