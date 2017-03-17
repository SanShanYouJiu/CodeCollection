package DesignMode.DesignModel.Factory.SimpleFactory;

/**
 * Created by han on 2017/1/19.
 */
public class BlackHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("黑色人种的皮肤颜色都是黑色的");
    }

    @Override
    public void talk() {
        System.out.println("黑人会说话 一般人听不懂");
    }
}
