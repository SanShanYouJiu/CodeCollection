package DesignMode.DesignModel.Bridge;

/**
 * Created by han on 2017/3/14.
 */
public class ShanZhaICorp extends Corp {

    public ShanZhaICorp(Product product) {
        super(product);
    }

    @Override
    public void makeMoney() {
        super.makeMoney();
        System.out.println("我赚钱啊...");
    }
}
