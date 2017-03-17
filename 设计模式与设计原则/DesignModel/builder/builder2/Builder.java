package DesignMode.DesignModel.builder.builder2;

/**
 * Created by han on 2017/3/2.
 */
public abstract class Builder {
    //设置产品的不同部分 以获得不同的产品
    public abstract void setPart();

    //建造产品
    public  abstract Product buildProduct();

}
