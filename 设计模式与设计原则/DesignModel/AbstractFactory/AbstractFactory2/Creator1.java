package DesignMode.DesignModel.AbstractFactory.AbstractFactory2;

/**
 * Created by han on 2017/1/19.
 */
public class Creator1 extends AbstractCreator {
    @Override
    public AbstractProductA createproductA() {
        return new ProductA1();
    }

    @Override
    public AbstractProductB createproductB() {
        return new ProductB1();
    }
}
