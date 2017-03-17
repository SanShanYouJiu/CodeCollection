package DesignMode.DesignModel.Decorate.Decorate2;

/**
 * Created by han on 2017/2/28.
 */
public class ConcreteDecorator2 extends  Decorator {

    //定义被修饰者
    public ConcreteDecorator2(Component _component) {
        super(_component);
    }

    //定义自己的修饰方法
    public void method2(){
        System.out.println("method2 修饰");
    }

    //重写父类的Operation方法
    public void operate(){
        super.operate();
        this.method2();
    }

}
