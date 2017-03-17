package DesignMode.DesignModel.AbstractFactory.AbstractFactory2;

/**
 * Created by han on 2017/1/19.
 */
public class Client  {

    public static void main(String[] args) {
        AbstractCreator creator1 = new Creator1();
        AbstractCreator creator2 = new Creator2();
        AbstractProductA a1 = creator1.createproductA();
        AbstractProductB b1 = creator1.createproductB();
        AbstractProductA a2 = creator2.createproductA();
        AbstractProductB b2 =creator2.createproductB();

    }
}
