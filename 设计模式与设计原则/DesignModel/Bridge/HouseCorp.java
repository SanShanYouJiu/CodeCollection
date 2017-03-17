package DesignMode.DesignModel.Bridge;

/**
 * Created by han on 2017/3/14.
 */
public class HouseCorp extends Corp {

    public HouseCorp(Product product) {
        super(product);
    }

    @Override
    public void makeMoney() {
        super.makeMoney();
        System.out.println("房产公司赚大钱了...");
    }
}
