package DesignMode.DesignModel.AbstractFactory;

/**
 * Created by han on 2017/1/19.
 */
public  abstract class AbstractBlackHuman implements Human  {
    @Override
    public void getColor() {
        System.out.println("黑种人");
    }

    @Override
    public void talk() {
        System.out.println("黑人说话一般人听不懂");
    }

}
