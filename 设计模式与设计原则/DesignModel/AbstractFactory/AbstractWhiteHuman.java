package DesignMode.DesignModel.AbstractFactory;

/**
 * Created by han on 2017/1/19.
 */
public abstract class AbstractWhiteHuman implements Human{
    @Override
    public void getColor() {
        System.out.println("白色人种");
    }

    @Override
    public void talk() {
        System.out.println("白人说话单字节");
    }


}
