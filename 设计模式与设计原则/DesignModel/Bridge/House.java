package DesignMode.DesignModel.Bridge;

/**
 * Created by han on 2017/3/14.
 */
public class House extends Product {
    @Override
    public void beProducted() {
        System.out.println("生产的房子是这样的....");
    }

    @Override
    public void beSelled() {
        System.out.println("生产的房子卖出去了 ....");
    }

}
