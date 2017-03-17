package DesignMode.DesignModel.Factory.SimpleFactory;

/**
 * Created by han on 2017/1/19.
 */
public class WhiteHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("白色人的皮肤颜色是白色的");
    }

    @Override
    public void talk() {
        System.out.println("白色人种会说话 一般是单字节");
    }
}
