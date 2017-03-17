package DesignMode.DesignModel.Bridge;

/**
 * Created by han on 2017/3/14.
 */
public class IPod extends Product {
    @Override
    public void beProducted() {
        System.out.println("生产出的IPod就是这样...");
    }

    @Override
    public void beSelled() {
        System.out.println("生产出的Ipod卖出去的...");
    }

}
