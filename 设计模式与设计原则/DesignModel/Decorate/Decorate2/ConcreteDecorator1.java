package DesignMode.DesignModel.Decorate.Decorate2;

/**
 * Created by han on 2017/2/28.
 */
public class ConcreteDecorator1 extends Decorator {

    //定义被修饰者
    public ConcreteDecorator1(Component _component) {
        super(_component);
    }

    //定义自己的修饰方法
    public void method1() {
        System.out.println("method 修饰");
    }

    //重写父类的Operation方法
    @Override
    public void operate() {
        this.method1();
        super.operate();
    }

}
