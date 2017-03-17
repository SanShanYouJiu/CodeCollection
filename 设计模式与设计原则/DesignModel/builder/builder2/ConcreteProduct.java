package DesignMode.DesignModel.builder.builder2;

/**
 * Created by han on 2017/3/2.
 */
public class ConcreteProduct extends Builder {
    private Product product = new Product();
    @Override
    public void setPart() {
        /**
         * 产品类的逻辑处理
          */
    }

    //组建一个产品
    @Override
    public Product buildProduct() {
        return product;
    }
}
