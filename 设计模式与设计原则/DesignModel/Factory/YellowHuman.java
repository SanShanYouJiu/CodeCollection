package DesignMode.DesignModel.Factory;

/**
 * Created by han on 2017/1/19.
 */
public class YellowHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("黄色人种的皮肤是黄色的");
    }

    @Override
    public void talk() {
        System.out.println("黄色人说话 一般是双字节");
    }
}
