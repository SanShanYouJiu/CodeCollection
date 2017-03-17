package DesignMode.DesignModel.AbstractFactory;

/**
 * Created by han on 2017/1/19.
 */
public abstract class AbstractYellowHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("黄种人");
    }

    @Override
    public void talk() {
        System.out.println("黄种人说双字节");
    }
}
