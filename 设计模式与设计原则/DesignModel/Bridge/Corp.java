package DesignMode.DesignModel.Bridge;

/**
 * Created by han on 2017/3/14.
 */
public abstract class Corp {
    private Product product;

    public Corp(Product product) {
        this.product = product;
    }

    public void makeMoney() {
        this.product.beProducted();
        this.product.beSelled();
    }


}
